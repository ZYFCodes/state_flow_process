package org.zyf.stateflowprocess.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zyf.stateflowprocess.common.utils.ZyfBeanFactoryUtils;
import org.zyf.stateflowprocess.mapper.BusinessTaskManageMapper;
import org.zyf.stateflowprocess.model.entity.BusinessTaskManageDo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanfengzhang
 * @description 任务处理的基类，包含任务处理的通用逻辑；
 * * 核心逻辑：
 * * 被提交的processor交由线程池执行；
 * * 每个processor关联一个ping对象，ping实现心跳逻辑；
 * * 每个processor在开始前需要有一定逻辑更新task的状态，否则可能导致任务被重复提交。
 * @date 2021/4/25  23:16
 */
public abstract class AbstractProcessor implements Runnable {
    private final Ping ping;

    private final BusinessTaskManageDo value;

    private final Semaphore semaphore = new Semaphore(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8,
            16, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new ThreadFactory() {
        private AtomicInteger threadCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "thread-processor-" + threadCount.getAndIncrement());
        }
    });

    protected AbstractProcessor(BusinessTaskManageDo value) {
        ping = new Ping(this);
        this.value = value;
    }

    /**
     * 心跳。
     * 关联到外部的Processor，当processor运行结束时，结束心跳。
     * 每个ping关联一个单独的ScheduledExecutorService，结束ping时直接shutdown线程池。
     */
    class Ping implements Runnable {
        private WeakReference<AbstractProcessor> weakReference;
        private ReferenceQueue<AbstractProcessor> referenceQueue = new ReferenceQueue<>();
        private ScheduledExecutorService scheduleAtFixedRate = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "task-ping"));
        private BusinessTaskManageMapper businessTaskManageMapper;

        Ping(AbstractProcessor processor) {
            weakReference = new WeakReference<>(processor, referenceQueue);
            businessTaskManageMapper = ZyfBeanFactoryUtils.getBean(BusinessTaskManageMapper.class);
        }

        void ping() {
            if (referenceQueue.poll() != null) { //兜底。当其关联的processor被垃圾回收后，结束心跳。
                LOGGER.warn("【任务处理心跳】taskId:{}的心跳被动结束", value.getId());
                scheduleAtFixedRate.shutdown();
            } else {
                try {
                    int curTime = (int) (System.currentTimeMillis() / 1000);
                    businessTaskManageMapper.updateLastPingTime(value.getId(), curTime);
                    LOGGER.warn("【任务处理心跳】taskId:{}心跳正常，当前时间:{} processor:{}", value.getId(), curTime, weakReference.get());
                } catch (Exception e) {
                    LOGGER.error("【任务处理心跳】taskId:{}心跳时间更新异常，exception：", value.getId(), e);
                }
            }
        }

        @Override
        public void run() {
            ping();
        }

        void start() {
            LOGGER.warn("【任务处理心跳】taskId:{}心跳正常开启", value.getId());
            scheduleAtFixedRate.scheduleWithFixedDelay(this, 2, 2, TimeUnit.SECONDS);
        }

        void close() {
            LOGGER.warn("【任务处理心跳】taskId:{}心跳正常结束", value.getId());
            scheduleAtFixedRate.shutdown();
        }
    }

    protected abstract void actualProcess(BusinessTaskManageDo value);

    protected abstract void end(BusinessTaskManageDo value);

    private void done() {
        /*todo 实际JVM在该处有未关闭的情况，后续当流量增大的时候需要留意一下*/
        ping.close();
        semaphore.release(1);
    }

    public final void process() {
        THREAD_POOL_EXECUTOR.submit(this);
    }

    public final boolean allowRecycle() {
        return semaphore.tryAcquire();
    }

    @Override
    public final void run() {
        this.ping.start();
        try {
            actualProcess(value);
        } finally {
            end(value);
            done();
        }
    }
}

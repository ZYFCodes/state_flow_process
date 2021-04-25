package org.zyf.stateflowprocess.processor;

/**
 * @author yanfengzhang
 * @description 任务处理的基类，包含任务处理的通用逻辑；
 *  * 核心逻辑：
 *  * 被提交的processor交由线程池执行；
 *  * 每个processor关联一个ping对象，ping实现心跳逻辑；
 *  * 每个processor在开始前需要有一定逻辑更新task的状态，否则可能导致任务被重复提交。
 * @date 2021/4/25  23:16
 */
public abstract class AbstractProcessor implements Runnable {
}

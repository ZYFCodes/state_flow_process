package org.zyf.stateflowprocess.dataprocess.datareader;

import org.zyf.stateflowprocess.common.exception.ReadException;

import java.util.concurrent.TimeUnit;

public interface AReader<T> {
    /**
     * @return 下一条数据
     * @throws ReadException 读取异常
     * @description 获取下一条数据内容
     */
    T getNext() throws ReadException;

    /**
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return 下一条数据
     * @throws ReadException 读取异常
     * @description 获取下一条数据内容（忽略指定异常信息）
     */
    T getNextIgnoreInterrupt(long timeout, TimeUnit timeUnit) throws ReadException;

    /**
     * @return 读取数据指定名称
     * @description 获取读取数据指定名称
     */
    String getName();
}

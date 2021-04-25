package org.zyf.stateflowprocess.common.exception;

/**
 * @author yanfengzhang
 * @description 读取数据异常
 * @date 2021/4/25  23:06
 */
public class ReadException extends Exception {
    public ReadException() {
        super();
    }

    public ReadException(String message) {
        super(message);
    }

    public ReadException(Throwable cause) {
        super(cause);
    }
}

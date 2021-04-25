package org.zyf.stateflowprocess.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dispatch {
    /**
     * 辅助基建表
     */
    int table();

    /**
     * 流转状态
     */
    int[] status();
}

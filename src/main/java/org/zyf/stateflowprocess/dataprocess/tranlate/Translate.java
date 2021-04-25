package org.zyf.stateflowprocess.dataprocess.tranlate;

import java.util.List;

public interface Translate<T> {
    /**
     * @param values 具体待转换数据
     * @return 转换后的数据
     * @description 转换指定数据类型
     */
    List<T> translate(List<T> values);
}

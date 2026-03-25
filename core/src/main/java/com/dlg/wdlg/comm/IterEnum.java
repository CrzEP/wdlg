package com.dlg.wdlg.comm;

import com.dlg.wdlg.exception.BuinessException;

/**
 * 迭代枚举接口
 *
 * @param <T>
 */
public interface IterEnum<T> {

    T getCode();

    /**
     * 根据 code 查找枚举
     */
    static <E extends Enum<E> & IterEnum<T>, T> E of(Class<E> enumClass, T code) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        throw new BuinessException(
                "No enum constant " + enumClass.getSimpleName() + " with code " + code
        );
    }

    /**
     * 返回null
     */
    static <E extends Enum<E> & IterEnum<T>, T> E ofNullable(Class<E> enumClass, T code) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

}

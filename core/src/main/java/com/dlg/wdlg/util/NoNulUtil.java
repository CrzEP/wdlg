package com.dlg.wdlg.util;

import com.dlg.wdlg.exception.BusinessException;

import java.util.Collection;

/**
 * 非空判断
 */
public class NoNulUtil {

    /**
     * 记录非空,否则抛出异常
     *
     * @param obj 记录对象
     */
    public static void record(Object obj) {
        if (null == obj) {
            throw new BusinessException("record not found");
        }
    }

    /**
     * 集合非空,否则抛出异常
     *
     * @param collection 集合
     */
    public static void collect(Collection collection) {
        if (null == collection) {
            throw new BusinessException("collection is empty");
        }
        if (collection.isEmpty()) {
            throw new BusinessException("collection is empty");
        }
    }

}

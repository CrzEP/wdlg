package com.dlg.wdlg.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Java实例拷贝工具类：支持浅拷贝（单对象/集合）
 *
 * @author lingui
 */
public class BeanCopyUtils {

    public interface CallBack<S, T> {

        /**
         * 回调方法
         *
         * @param src    源对象
         * @param target 目的对象
         */
        void callBack(S src, T target);

    }

    /**
     * 浅拷贝单个对象：基于Spring BeanUtils实现
     *
     * @param source    源对象（非null）
     * @param targetCls 目标对象Class
     * @return 拷贝后的目标对象
     */
    @SneakyThrows
    public static <S, T> T copy(S source, Class<T> targetCls) {
        if (Objects.isNull(source)) {
            return null;
        }
        // 反射创建目标对象实例
        T target = targetCls.newInstance();
        // 核心：复制属性（浅拷贝）
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 浅拷贝集合：批量处理对象拷贝
     *
     * @param source    源对象集合（非null）
     * @param targetCls 目标对象Class
     * @return 拷贝后的目标对象集合
     */
    @SneakyThrows
    public static <S, T> List<T> listCopy(Collection<S> source, Class<T> targetCls) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        List<T> list = Lists.newArrayList();
        source.forEach(s -> list.add(copy(s, targetCls)));
        return list;
    }

    /**
     * 浅拷贝集合：批量处理对象拷贝 附带处理回调函数
     *
     * @param source    源集合
     * @param targetCls 目的对象
     * @param callBack  回调函数
     * @param <S>       源对象类型
     * @param <T>       目的对象类型
     * @return 新集合
     */
    public static <S, T> List<T> listCopy(Collection<S> source, Class<T> targetCls, CallBack<S, T> callBack) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        List<T> list = Lists.newArrayList();
        for (S s : source) {
            T t = copy(s, targetCls);
            callBack.callBack(s, t);
            list.add(t);
        }
        return list;
    }

    /**
     * 复制非空属性
     *
     * @param src    src
     * @param target target
     */
    public static void copyPropertiesNoNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 获取空属性名
     *
     * @param source 源对象
     * @return 属性数组
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (ObjectUtils.isEmpty(srcValue)) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
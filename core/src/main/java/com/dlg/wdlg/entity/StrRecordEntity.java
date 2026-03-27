package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 字符记录，用于快速存取内容
 */
@Data
@TableName("t_str_record")
public class StrRecordEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 关键词,用于定义同类记录字符区别
     */
    private String keyword;

    /**
     * 内容
     */
    private String content;

    /**
     * 数字tag
     */
    private Long intTag;

    /**
     * 字符tag,多个tag,重新存入一行
     */
    private String strTag;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 状态: 无定义
     */
    private Integer recordState;

    private Date createTime;

}

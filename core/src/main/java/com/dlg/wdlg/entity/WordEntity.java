package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 单词表
 * 单词是唯一的
 */
@Data
@TableName("t_word")
public class WordEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 单词/词组
     */
    private String word;

    /**
     * 状态：1正常 2停用
     */
    private Integer available;

    private Date createTime;

}

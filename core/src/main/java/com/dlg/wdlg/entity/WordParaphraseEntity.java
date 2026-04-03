package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 单词释意表
 *
 */
@Data
@TableName("t_word_paraphrase")
public class WordParaphraseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 单词ID
     */
    private Long wordId;

    /**
     * 释意
     */
    private String paraphrase;

    /**
     * 词性
     */
    private String pos;

    private Date createTime;

}

package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 单词例句表
 *
 */
@Data
@TableName("t_word_sentence")
public class WordSentenceEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 单词ID
     */
    private Long wordId;

    /**
     * 句子
     */
    private String sentence;

    /**
     * 句子中文解释
     */
    private String sentenceCn;

    /**
     * 排序
     */
    private Integer sort;

    private Date createTime;

}

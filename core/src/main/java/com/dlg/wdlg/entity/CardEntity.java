package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 卡片表
 */
@Data
@TableName("t_card")
public class CardEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 所属
     */
    private Long belongUserId;

    /**
     * 内容:单词Id、文本类块知识ID、树知识ID、网络知识ID
     */
    private Long contentId;

    /**
     * 类型：1 单词卡 2文本知识 3树知识 4网络知识
     * 前期只有单词卡
     */
    private Integer type;

    /**
     * 状态：1正常 2停用
     */
    private Integer state;

    private Date createTime;

}

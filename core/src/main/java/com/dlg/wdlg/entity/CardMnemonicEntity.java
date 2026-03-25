package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 助记表
 */
@Data
@TableName("t_card_mnemonic")
public class CardMnemonicEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 所属卡片
     */
    private Long belongCardId;

    /**
     * 类型：1 句子 2记忆方式 3发音文件aiff
     */
    private Integer type;

    /**
     * 排序编号
     */
    private int sort;

    /**
     * 状态：1正常 2停用
     */
    private Integer state;

    private Date createTime;

}

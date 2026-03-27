package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 卡片关联表/中间表
 */
@Data
@TableName("t_card_relative")
public class CardRelativeEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 所属卡片
     */
    private Long belongCardId;

    /**
     * 关联卡片
     */
    private Long relativeCardId;

    private Integer sort;

    /**
     * 关联标签：空代表 单词关联
     */
    private String tag;

}

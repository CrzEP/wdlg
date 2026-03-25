package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 关联单词
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

    private int sort;

}

package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 卡片分组表
 */
@Data
@TableName("t_card_group")
public class CardGroupEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 状态：1正常 2停用
     */
    private Integer available;

    /**
     * 0原始卡 1用户分组
     */
    private Integer type;

    /**
     * 0公共卡
     */
    private Long belongUserId;

    private Date createTime;

}

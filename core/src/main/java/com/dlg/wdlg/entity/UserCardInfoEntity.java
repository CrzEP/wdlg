package com.dlg.wdlg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user_card_info")
public class UserCardInfoEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 记忆卡ID
     */
    private Long cardId;

    /**
     * 用户ID
     */
    private Long belongUserId;

    /**
     * 所属分组
     */
    private Long belongGroupId;

    /**
     * 1正常 2延迟记忆 3避免记忆
     */
    private Integer delay;

    /**
     * 状态 1熟记 2模糊 3陌生
     */
    private Integer state;

    /**
     * 第一次记忆/留下日志的时间
     */
    private Long firstMemoryTime;

    /**
     * 最后一次记忆时间, 0 表示未开始
     */
    private Long lastMemoryTime;

    /**
     * 下次记忆时间，小于当前时间则认为超时
     */
    private Long nextMemoryTime;

    /**
     * 难度系数
     */
    private Double difficultyIndex;

    /**
     * 稳定系数
     */
    private Double stabilityIndex;

    /**
     * 掌握系数/间隔系数
     */
    private Integer masterIndex;

    /**
     * 记忆当前卡耗时 单位毫秒
     */
    private Long cardCostMils;

    /**
     * 记忆当前卡跨度[起始天，结束天） 单位天
     */
    private Long cardCostDay;

    /**
     * 记忆次数
     */
    private Integer memoryCount;

    private Date createTime;
}

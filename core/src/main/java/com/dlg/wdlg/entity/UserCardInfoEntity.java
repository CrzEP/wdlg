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
    private Long userId;

    /**
     * 1正常 2延迟记忆 3避免记忆
     */
    private int delay;

    /**
     * 状态
     */
    private int state;

    /**
     * 第一次记忆/留下日志的时间
     */
    private Long firstMemoryTime;

    /**
     * 最后一次记忆时间, 0 表示未开始
     */
    private Long lastMemoryTime;

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
    private int memoryCount;

    /**
     * 下次记忆时间，小于当前时间则认为超时
     */
    private Long nextMemoryTime;

    /**
     * 难度系数
     */
    private int problemIndex;

    /**
     * 掌握系数
     */
    private int masterIndex;

    private Date createTime;
}

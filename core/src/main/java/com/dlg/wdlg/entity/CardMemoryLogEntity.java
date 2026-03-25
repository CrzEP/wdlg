package com.dlg.wdlg.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 卡片记忆日志表
 */
@Data
@TableName("t_card_memory_log")
public class CardMemoryLogEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属卡片
     */
    private Long belongCardId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 状态：1熟悉 2模糊 3陌生
     */
    private Integer state;

    /**
     * 记忆时间戳/操作时的记忆时间
     */
    private Long memoryTime;

    /**
     * 起始记忆时间
     */
    private Long startMemoryTime;

    /**
     * 结束记忆时间
     */
    private Long endMemoryTime;

    /**
     * 本次耗时
     */
    private Long costTime;

    private Date createTime;
}

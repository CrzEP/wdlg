package com.dlg.wdlg.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

/**
 * 用户记忆配置
 */
@Data
@TableName("t_user_memory_config")
public class UserMemoryConfigEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 每日记忆个数：新词个数
     */
    private int dayMemory;

    /**
     * 每日复习个数：旧词个数
     */
    private int dayReview;

    /**
     * 记忆算法配置json
     */
    private String memoryAlgoConfig;

    /**
     * 每日刷新时间：默认凌晨4点
     */
    private LocalTime refreshTime;

    private Date createTime;

}

package com.dlg.wdlg.pojos;

import lombok.Data;

@Data
public class TodayCardInfoPojo {

    /**
     * 需要记忆的单词数量
     */
    private int needMemory;

    /**
     * 预估耗时：分钟
     */
    private int predictTime;

    /**
     * 新词数
     */
    private int newWordNum;

    /**
     * 复习数
     */
    private int reviewNum;

    /**
     * 连续完成天数
     */
    private int successiveDays;

}

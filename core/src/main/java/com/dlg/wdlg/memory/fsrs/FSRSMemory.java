package com.dlg.wdlg.memory.fsrs;

import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.memory.MemoryAdapter;
import io.github.openspacedrepetition.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * FSRS 间隔重复学习算法
 * @see https://github.com/open-spaced-repetition/java-fsrs
 * 核心：记忆概率模型 + 时间间隔优化
 * 适合简单知识、短期记忆、复习调度
 */
@Slf4j
@Component
public class FSRSMemory implements MemoryAdapter {

    // 构建 Scheduler
    private final Scheduler scheduler = Scheduler.builder().build();

    @Override
    public void review(UserCardInfoEntity entity, UserCardStateEnum state, long memoryTime) {
        // 复习
        Card card = FSRSUtil.covertToCard(entity);
        CardAndReviewLog reviewLog = scheduler.reviewCard(card, Rating.GOOD, Instant.ofEpochMilli(memoryTime));
        card = reviewLog.card();
        FSRSUtil.updateFromCard(card,entity);
        ReviewLog reviewedLog = reviewLog.reviewLog();
        CardMemoryLogEntity memoryLogEntity = FSRSUtil.covertToMemoryLog(reviewedLog);
        updateReviewInfo(entity,memoryLogEntity);
    }

    @Override
    public void updateReviewInfo(UserCardInfoEntity entity, CardMemoryLogEntity memoryLogEntity) {
        // todo
    }


}

package com.dlg.wdlg.memory.fsrs;

import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.memory.MemoryAdapter;
import com.dlg.wdlg.memory.SchedulerCore;
import com.dlg.wdlg.pojos.CardUserAndLogInfo;
import io.github.openspacedrepetition.Card;
import io.github.openspacedrepetition.CardAndReviewLog;
import io.github.openspacedrepetition.ReviewLog;
import io.github.openspacedrepetition.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * FSRS记忆算法
 *
 */
@Slf4j
@Component
public class FSRSMemory implements MemoryAdapter {

    /**
     * 算法核心
     */
    Scheduler scheduler = SchedulerCore.INSTANCE();

    @Override
    public CardUserAndLogInfo review(UserCardInfoEntity entity, UserCardStateEnum state, long memoryTime) {
        // 转换操作
        Card card = FSRSUtil.covertToCard(entity);
        // 复习
        CardAndReviewLog cardAndReviewLog = scheduler.reviewCard(card,
                FSRSUtil.codeToRating(state.getCode()), Instant.ofEpochMilli(memoryTime));
        // 获取卡片
        card = cardAndReviewLog.card();
        // 更新卡片
        FSRSUtil.updateFromCard(card, entity);
        // 获取日志
        ReviewLog reviewedLog = cardAndReviewLog.reviewLog();
        CardMemoryLogEntity memoryLogEntity = FSRSUtil.covertToMemoryLog(reviewedLog);
        return new CardUserAndLogInfo(entity, memoryLogEntity);
    }

}

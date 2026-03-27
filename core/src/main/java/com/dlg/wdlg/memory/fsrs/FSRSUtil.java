package com.dlg.wdlg.memory.fsrs;

import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import io.github.openspacedrepetition.Card;
import io.github.openspacedrepetition.ReviewLog;
import io.github.openspacedrepetition.State;

import java.time.Instant;

/**
 * FSRS算法工具类
 */
public class FSRSUtil {

    /**
     * 状态
     *
     * @param state 状态
     * @return state
     */
    public static State toState(int state) {
        switch (state) {
            case 1:
                // 熟悉
                return State.LEARNING;
            case 2:
                // 陌生
                return State.REVIEW;
            case 3:
                // 忘记
                return State.RELEARNING;
            default:
                throw new BusinessException("error state code");
        }
    }

    /**
     * 转换卡对象
     *
     * @param entity 用户卡
     * @return card
     */
    public static Card covertToCard(UserCardInfoEntity entity) {
        int id = Math.toIntExact(entity.getId());
        return Card.builder().cardId(id)
                .step(entity.getMasterIndex())
                .state(FSRSUtil.toState(entity.getState()))
                .stability(entity.getStabilityIndex())
                .difficulty(entity.getDifficultyIndex())
                .due(Instant.ofEpochMilli(entity.getNextMemoryTime()))
                .lastReview(Instant.ofEpochMilli(entity.getLastMemoryTime()))
                .build();
    }

    /**
     * 转 卡 记忆日志
     * @param reviewedLog 复习日志
     * @return 记忆日志
     */
    public static CardMemoryLogEntity covertToMemoryLog(ReviewLog reviewedLog) {
        CardMemoryLogEntity entity = new CardMemoryLogEntity();
        entity.setBelongCardId((long) reviewedLog.cardId());
        UserCardStateEnum state = ratingToState(reviewedLog.rating().getValue());
        entity.setState(state.getCode());
        entity.setNextMemoryTime(reviewedLog.reviewDatetime().getEpochSecond());
        return entity;
    }

    /**
     * 评分转状态
     *
     * @param rating 评分
     * @return 状态
     */
    public static UserCardStateEnum ratingToState(int rating) {
        switch (rating) {
            case 1:
            case 2:
                return UserCardStateEnum.UNFAMILIAR;
            case 3:
                return UserCardStateEnum.BLUR;
            case 4:
                return UserCardStateEnum.FAMILIAR;
            default:
                throw new BusinessException("error rating code");
        }
    }

    /**
     * 更新
     * @param card card
     * @param entity entity
     */
    public static void updateFromCard(Card card, UserCardInfoEntity entity) {
        entity.setMasterIndex(card.getStep());
        entity.setStabilityIndex(card.getStability());
        entity.setDifficultyIndex(card.getDifficulty());
        entity.setNextMemoryTime(card.getDue().getEpochSecond());
        entity.setLastMemoryTime(card.getLastReview().getEpochSecond());
        entity.setState(card.getState().getValue());
    }
}

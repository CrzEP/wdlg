package com.dlg.wdlg.memory.fsrs;

import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.exception.BusinessException;
import io.github.openspacedrepetition.Card;
import io.github.openspacedrepetition.Rating;
import io.github.openspacedrepetition.ReviewLog;
import io.github.openspacedrepetition.State;

import java.time.Instant;

/**
 * FSRS算法工具类
 */
public class FSRSUtil {

    /**
     * 转换卡对象
     *
     * @param entity 用户卡
     * @return card
     */
    public static Card covertToCard(UserCardInfoEntity entity) {
        int id = Math.toIntExact(entity.getId());
        // id 不可变标记卡片唯一
        return Card.builder().cardId(id)
                // 掌握程度
                .step(entity.getMasterIndex())
                // 状态
                .state(toState(entity.getState()))
                // 稳定性：能记多久
                .stability(entity.getStabilityIndex())
                // 难度：卡片本身难度
                .difficulty(entity.getDifficultyIndex())
                // 应该被复习的时间点
                .due(Instant.ofEpochMilli(entity.getNextMemoryTime()))
                // 最后一次复习
                .lastReview(Instant.ofEpochMilli(entity.getLastMemoryTime()))
                .build();
    }

    /**
     * 转 卡 记忆日志
     *
     * @param reviewedLog 复习日志
     * @return 记忆日志
     */
    public static CardMemoryLogEntity covertToMemoryLog(ReviewLog reviewedLog) {
        CardMemoryLogEntity entity = new CardMemoryLogEntity();
        entity.setBelongCardId((long) reviewedLog.cardId());
        entity.setRating(rating(reviewedLog.rating()));
        long start = reviewedLog.reviewDatetime().getEpochSecond();
        entity.setStartMemoryTime(start);
        long cost = reviewedLog.reviewDuration().longValue();
        long end = start + cost;
        entity.setEndMemoryTime(end);
        entity.setCostTime(cost);
        entity.setTag("reviewedLog");
        return entity;
    }

    /**
     * 转 卡 记忆日志
     *
     * @param entity 复习日志
     * @return 记忆日志
     */
    public static ReviewLog covertToReviewLog(CardMemoryLogEntity entity) {
        return new ReviewLog(
                entity.getId(), codeToRating(entity.getRating()),
                Instant.ofEpochMilli(entity.getStartMemoryTime()),
                Math.toIntExact(entity.getCostTime())
        );
    }


    /**
     * 更新
     *
     * @param card   card
     * @param entity entity
     */
    public static void updateFromCard(Card card, UserCardInfoEntity entity) {
        // 掌握程度
        entity.setMasterIndex(card.getStep());
        // 稳定性
        entity.setStabilityIndex(card.getStability());
        // 难度
        entity.setDifficultyIndex(card.getDifficulty());
        // 下次记忆时间
        entity.setNextMemoryTime(card.getDue().getEpochSecond());
        // 上次记忆时间
        entity.setLastMemoryTime(card.getLastReview().getEpochSecond());
        // 卡片状态
        entity.setState(toRecordState(card.getState()));
    }

    /**
     * 新卡
     *
     * @return 新卡参数
     */
    public static UserCardInfoEntity newCard() {
        UserCardInfoEntity entity = new UserCardInfoEntity();
        Card card = Card.builder().build();
        updateFromCard(card, entity);
        return entity;
    }

    /**
     * 状态: 卡状态
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
     * 卡状态转数据库状态
     *
     * @param state 状态
     * @return code
     */
    public static int toRecordState(State state) {
        switch (state) {
            case LEARNING:
                // 熟悉
                return 1;
            case REVIEW:
                // 陌生
                return 2;
            case RELEARNING:
                // 忘记
                return 3;
            default:
                throw new BusinessException("error state");
        }
    }

    /**
     * 日志/操作评分code
     *
     * @param rating 评分
     * @return code
     */
    public static int rating(Rating rating) {
        switch (rating) {
            case EASY:
                return 1;
            case HARD:
                return 2;
            case GOOD:
                return 3;
            case AGAIN:
                return 4;
            default:
                throw new BusinessException("error rating code");
        }
    }

    /**
     * 日志评分转
     *
     * @param code code
     * @return 评分
     */
    public static Rating codeToRating(int code) {
        switch (code) {
            case 1:
                return Rating.EASY;
            case 2:
                return Rating.GOOD;
            case 3:
                return Rating.HARD;
            case 4:
                return Rating.AGAIN;
            default:
                throw new BusinessException("error rating code");
        }
    }

}

package com.dlg.wdlg.memory;

import com.dlg.wdlg.config.configValue.MemoryValue;
import io.github.openspacedrepetition.Scheduler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 算法核心：未制定的参数使用默认参数
 * 不了解算法，使用默认参数即可。
 * Scheduler:  FSRS 记忆模型 + 遗忘曲线 + 目标记忆概率调度
 * RSRS:
 * 在“快要忘记”的时候复习，效率最高。即在保证成功率的前提下，减少复习次数
 */
@Component
@Slf4j
public class SchedulerCore {

    @Resource
    MemoryValue memoryValue;

    /**
     * 核心构造器
     */
    private static final Scheduler.Builder builder = Scheduler.builder();

    /**
     * 算法核心
     */
    private static volatile Scheduler SCHEDULER_CORE;

    @PostConstruct
    public void init() {
        // 开启模糊
        builder.enableFuzzing(memoryValue.getEnableFuzzing());
        log.info("算法核心参数：{}", INSTANCE().toJson());
    }

    /**
     * 获取实例
     *
     * @return 单例
     */
    public static Scheduler INSTANCE() {
        if (SCHEDULER_CORE == null) {
            synchronized (SchedulerCore.class) {
                if (SCHEDULER_CORE == null) {
                    SCHEDULER_CORE = builder.build();
                }
            }
        }
        return SCHEDULER_CORE;
    }

}

package org.ltl.job;

import org.microjvm.di.annotation.Service;
import org.microjvm.scheduler.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Example scheduled jobs demonstrating all three schedule types.
 */
@Service
public class DatabaseCleanupJob {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleanupJob.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final AtomicLong healthCheckCount = new AtomicLong();
    private final AtomicLong statsReportCount = new AtomicLong();

    /**
     * Fixed-rate health check — runs every 30 seconds.
     */
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void healthCheck() {
        long count = healthCheckCount.incrementAndGet();
        logger.info("[HealthCheck #{}] System OK at {}", count, LocalDateTime.now().format(FMT));
    }

    /**
     * Fixed-delay stats reporter — waits 10 seconds between end of last run and start of next.
     * Has a 5-second initial delay before the first run.
     */
    @Scheduled(fixedDelay = 10, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void reportStats() {
        long count = statsReportCount.incrementAndGet();
        long freeMemoryMB = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long totalMemoryMB = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        logger.info("[Stats #{}] Memory: {}MB / {}MB | Threads: {}",
                count, freeMemoryMB, totalMemoryMB, Thread.activeCount());
    }

    /**
     * Cron-based cleanup — runs at the top of every hour.
     * Cron: second=0, minute=0, hour=every, day=every, month=every, dow=every
     */
    @Scheduled(cron = "0 0 * * * *")
    public void hourlyCleanup() {
        logger.info("[Cleanup] Running hourly cleanup at {}", LocalDateTime.now().format(FMT));
        // Simulate cleanup work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("[Cleanup] Hourly cleanup completed");
    }

    public long getHealthCheckCount() {
        return healthCheckCount.get();
    }

    public long getStatsReportCount() {
        return statsReportCount.get();
    }
}

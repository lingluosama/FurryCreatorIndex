package org.rookie.data.utils;

import com.mybatisflex.core.keygen.IKeyGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CustomIdGenerator implements IKeyGenerator {

    // 纪元开始时间:1982-1-1 11:45:14
    // 将这个时间设置为你项目上线的具体时间，可以最大化 ID 的有效期
    private final static long EPOCH;

    static {
        // 使用固定的纪元开始时间
        LocalDateTime epochDateTime = LocalDateTime.of(1982, 1, 1, 11, 45, 14);
        EPOCH = epochDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    private final long workerId; // 机器ID
    private final long workerIdBits = 5L; // 机器ID所占的位数
    private final long sequenceBits = 8L; // 序列号所占的位数

    private final long maxWorkerId = ~(-1L << workerIdBits); // 最大机器ID
    private final long maxSequence = ~(-1L << sequenceBits); // 最大序列号

    private final long workerIdShift = sequenceBits; // 机器ID左移位数
    private final long timestampShift = sequenceBits + workerIdBits; // 时间戳左移位数

    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    public CustomIdGenerator(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("节点Id不应该大于最大节点数 %d 或小于 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    @Override
    public Object generate(Object entity, String keyColumn) {
        return nextId();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上次生成ID的时间戳，说明系统时钟回退，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间戳，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & maxSequence;
            // 毫秒内序列号已用完，等待下一毫秒
            if (sequence == 0) {
                timestamp = tillNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，序列号重置
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 拼接成最终的ID
        return ((timestamp - EPOCH) << timestampShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    // 等待直到下一毫秒
    private long tillNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    // 获取当前时间戳
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
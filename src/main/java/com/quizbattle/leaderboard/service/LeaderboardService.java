package com.quizbattle.leaderboard.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;



@Service
public class LeaderboardService {

    private final StringRedisTemplate redisTemplate;


    private static final String PREFIX = "leaderboard:subject:";


    public LeaderboardService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



    private String getKey(UUID subjectId) {
        return PREFIX + subjectId;
    }



    public void updateRating(
            UUID subjectId,
            UUID userId,
            int rating
    ) {

        redisTemplate
                .opsForZSet()
                .add(
                        getKey(subjectId),
                        userId.toString(),
                        rating
                );

    }



    public Long getRank(
            UUID subjectId,
            UUID userId
    ) {

        Long rank =
                redisTemplate
                        .opsForZSet()
                        .reverseRank(
                                getKey(subjectId),
                                userId.toString()
                        );

        return rank == null ? null : rank + 1;
    }



    public Set<String> getTopPlayers(
            UUID subjectId,
            int limit
    ) {

        return redisTemplate
                .opsForZSet()
                .reverseRange(
                        getKey(subjectId),
                        0,
                        limit - 1
                );

    }

}
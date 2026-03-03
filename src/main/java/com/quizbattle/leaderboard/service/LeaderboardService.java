package com.quizbattle.leaderboard.service;

import com.quizbattle.leaderboard.dto.LeaderboardEntryDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<LeaderboardEntryDTO> getLeaderboardPage(
            UUID subjectId,
            int page,
            int size
    ) {

        String key = getKey(subjectId);

        long start = (long) page * size;
        long end = start + size - 1;

        Set<String> users =
                redisTemplate.opsForZSet()
                        .reverseRange(key, start, end);

        if (users == null)
            return List.of();

        List<LeaderboardEntryDTO> result = new ArrayList<>();

        for (String userIdStr : users) {

            UUID userId = UUID.fromString(userIdStr);

            Double score =
                    redisTemplate.opsForZSet()
                            .score(key, userIdStr);

            Long rank =
                    redisTemplate.opsForZSet()
                            .reverseRank(key, userIdStr);

            result.add(
                    new LeaderboardEntryDTO(
                            userId,
                            score != null ? score.intValue() : 0,
                            rank != null ? rank + 1 : 0
                    )
            );
        }

        return result;
    }

}
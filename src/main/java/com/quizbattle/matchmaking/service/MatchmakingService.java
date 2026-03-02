package com.quizbattle.matchmaking.service;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.service.MatchService;

import com.quizbattle.rating.entity.Rating;
import com.quizbattle.rating.service.RatingService;

import com.quizbattle.subject.entity.Subject;

import com.quizbattle.user.entity.User;

import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;



@Service
public class MatchmakingService {

    private final StringRedisTemplate redisTemplate;

    private final RatingService ratingService;

    private final MatchService matchService;



    private static final String PREFIX =
            "matchmaking:subject:";



    public MatchmakingService(
            StringRedisTemplate redisTemplate,
            RatingService ratingService,
            MatchService matchService
    ) {
        this.redisTemplate = redisTemplate;
        this.ratingService = ratingService;
        this.matchService = matchService;
    }



    private String getKey(UUID subjectId) {
        return PREFIX + subjectId;
    }



    public void joinQueue(
            User user,
            Subject subject
    ) {

        Rating rating =
                ratingService
                        .getOrCreateRating(user, subject);


        redisTemplate
                .opsForZSet()
                .add(
                        getKey(subject.getId()),
                        user.getId().toString(),
                        rating.getRating()
                );

    }



    public Match tryMatch(
            User user,
            Subject subject,
            User opponent
    ) {

        redisTemplate
                .opsForZSet()
                .remove(
                        getKey(subject.getId()),
                        user.getId().toString()
                );


        redisTemplate
                .opsForZSet()
                .remove(
                        getKey(subject.getId()),
                        opponent.getId().toString()
                );


        return matchService.createMatch(
                user,
                opponent,
                subject
        );

    }



    public Set<String> findCandidates(
            Subject subject,
            int minRating,
            int maxRating
    ) {

        return redisTemplate
                .opsForZSet()
                .rangeByScore(
                        getKey(subject.getId()),
                        minRating,
                        maxRating
                );

    }

}
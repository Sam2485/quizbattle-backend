package com.quizbattle.matchmaking.service;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.service.MatchService;

import com.quizbattle.rating.entity.Rating;
import com.quizbattle.rating.service.RatingService;

import com.quizbattle.subject.entity.Subject;

import com.quizbattle.user.entity.User;

import com.quizbattle.user.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;



@Service
public class MatchmakingService {

    private final StringRedisTemplate redisTemplate;

    private final RatingService ratingService;

    private final MatchService matchService;

    private final UserRepository userRepository;



    private static final String PREFIX =
            "matchmaking:subject:";



    public MatchmakingService(
            StringRedisTemplate redisTemplate,
            RatingService ratingService,
            MatchService matchService,
            UserRepository userRepository
    ) {
        this.redisTemplate = redisTemplate;
        this.ratingService = ratingService;
        this.matchService = matchService;
        this.userRepository = userRepository;
    }



    private String getKey(UUID subjectId) {
        return PREFIX + subjectId;
    }



    @Transactional
    public Match joinQueue(
            User user,
            Subject subject
    ) {

        Rating rating =
                ratingService
                        .getOrCreateRating(user, subject);


        String key = getKey(subject.getId());

        // Add user to Redis sorted set
        redisTemplate
                .opsForZSet()
                .add(
                        key,
                        user.getId().toString(),
                        rating.getRating()
                );


        // Try to get top 2 players
        Set<String> players =
                redisTemplate
                        .opsForZSet()
                        .reverseRange(key, 0, 1);


        if (players == null || players.size() < 2) {
            // Not enough players yet
            return null;
        }


        String[] ids = players.toArray(new String[0]);

        UUID player1Id = UUID.fromString(ids[0]);
        UUID player2Id = UUID.fromString(ids[1]);


        if (player1Id.equals(player2Id)) {
            return null;
        }


        // Remove matched players from queue
        redisTemplate.opsForZSet().remove(key, ids[0]);
        redisTemplate.opsForZSet().remove(key, ids[1]);


        User player1 =
                userRepository.findById(player1Id).orElseThrow();

        User player2 =
                userRepository.findById(player2Id).orElseThrow();


        // Create match
        return matchService.createMatch(
                player1,
                player2,
                subject
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
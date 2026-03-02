package com.quizbattle.match.service;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.repository.MatchRepository;

import com.quizbattle.rating.entity.Rating;
import com.quizbattle.rating.service.RatingService;

import com.quizbattle.subject.entity.Subject;

import com.quizbattle.user.entity.User;

import com.quizbattle.leaderboard.service.LeaderboardService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;



@Service
public class MatchService {

    private final MatchRepository matchRepository;

    private final RatingService ratingService;

    private final LeaderboardService leaderboardService;



    public MatchService(
            MatchRepository matchRepository,
            RatingService ratingService,
            LeaderboardService leaderboardService
    ) {
        this.matchRepository = matchRepository;
        this.ratingService = ratingService;
        this.leaderboardService = leaderboardService;
    }



    @Transactional
    public Match createMatch(
            User player1,
            User player2,
            Subject subject
    ) {

        Match match =
                new Match(player1, player2, subject);

        return matchRepository.save(match);
    }



    @Transactional
    public Match startMatch(UUID matchId) {

        Match match =
                matchRepository
                        .findById(matchId)
                        .orElseThrow();

        match.setStatus("IN_PROGRESS");

        match.setStartedAt(Instant.now());

        return matchRepository.save(match);
    }



    @Transactional
    public Match finishMatch(
            UUID matchId,
            User winner
    ) {

        Match match =
                matchRepository
                        .findById(matchId)
                        .orElseThrow();


        match.setStatus("FINISHED");

        match.setFinishedAt(Instant.now());

        match.setWinner(winner);


        User player1 = match.getPlayer1();

        User player2 = match.getPlayer2();

        Subject subject = match.getSubject();


        Rating rating1 =
                ratingService.getOrCreateRating(player1, subject);

        Rating rating2 =
                ratingService.getOrCreateRating(player2, subject);


        boolean player1Won =
                winner.getId().equals(player1.getId());


        ratingService.updateRatings(
                rating1,
                rating2,
                player1Won
        );


        leaderboardService.updateRating(
                subject.getId(),
                player1.getId(),
                rating1.getRating()
        );

        leaderboardService.updateRating(
                subject.getId(),
                player2.getId(),
                rating2.getRating()
        );


        matchRepository.save(match);

        return match;
    }

}
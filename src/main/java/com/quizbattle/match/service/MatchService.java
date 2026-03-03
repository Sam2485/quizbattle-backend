package com.quizbattle.match.service;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.entity.MatchAnswer;
import com.quizbattle.match.repository.MatchRepository;
import com.quizbattle.match.repository.MatchAnswerRepository;

import com.quizbattle.question.entity.Question;
import com.quizbattle.question.service.QuestionService;

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
    private final MatchAnswerRepository matchAnswerRepository;
    private final QuestionService questionService;
    private final RatingService ratingService;
    private final LeaderboardService leaderboardService;



    public MatchService(
            MatchRepository matchRepository,
            MatchAnswerRepository matchAnswerRepository,
            QuestionService questionService,
            RatingService ratingService,
            LeaderboardService leaderboardService
    ) {
        this.matchRepository = matchRepository;
        this.matchAnswerRepository = matchAnswerRepository;
        this.questionService = questionService;
        this.ratingService = ratingService;
        this.leaderboardService = leaderboardService;
    }



    // ✅ REQUIRED BY MATCHMAKING SERVICE
    @Transactional
    public Match createMatch(
            User player1,
            User player2,
            Subject subject
    ) {

        Match match = new Match(player1, player2, subject);

        match.setStatus("WAITING");

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



    // 🔥 CORE ANSWER SUBMISSION ENGINE
    @Transactional
    public Match submitAnswer(
            UUID matchId,
            User user,
            Question question,
            String submittedAnswer,
            Long timeTakenMs
    ) {

        Match match = matchRepository
                .findById(matchId)
                .orElseThrow();


        boolean isCorrect =
                questionService.isCorrectAnswer(
                        question,
                        submittedAnswer
                );


        MatchAnswer answer =
                new MatchAnswer(
                        match,
                        user,
                        question,
                        submittedAnswer,
                        isCorrect,
                        timeTakenMs
                );

        matchAnswerRepository.save(answer);

        checkAndFinishMatch(match);

        return match;
    }



    private void checkAndFinishMatch(Match match) {

        User player1 = match.getPlayer1();
        User player2 = match.getPlayer2();

        int totalQuestions = 5; // configurable later


        long totalAnswersPlayer1 =
                matchAnswerRepository
                        .findByMatchAndUser(match, player1)
                        .size();

        long totalAnswersPlayer2 =
                matchAnswerRepository
                        .findByMatchAndUser(match, player2)
                        .size();


        if (totalAnswersPlayer1 < totalQuestions ||
                totalAnswersPlayer2 < totalQuestions)
            return;


        long correct1 =
                matchAnswerRepository
                        .countCorrectAnswers(match, player1);

        long correct2 =
                matchAnswerRepository
                        .countCorrectAnswers(match, player2);


        User winner;


        if (correct1 > correct2) {
            winner = player1;
        } else if (correct2 > correct1) {
            winner = player2;
        } else {

            long time1 =
                    matchAnswerRepository
                            .totalTimeTaken(match, player1);

            long time2 =
                    matchAnswerRepository
                            .totalTimeTaken(match, player2);

            winner =
                    time1 <= time2
                            ? player1
                            : player2;
        }


        finishMatch(match, winner);
    }



    @Transactional
    private void finishMatch(
            Match match,
            User winner
    ) {

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
    }

    public Match getMatchById(UUID matchId) {
        return matchRepository
                .findById(matchId)
                .orElseThrow();
    }
}
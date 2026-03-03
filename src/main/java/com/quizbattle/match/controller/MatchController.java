package com.quizbattle.match.controller;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.service.MatchService;

import com.quizbattle.question.entity.Question;
import com.quizbattle.question.repository.QuestionRepository;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final UserService userService;
    private final QuestionRepository questionRepository;



    public MatchController(
            MatchService matchService,
            UserService userService,
            QuestionRepository questionRepository
    ) {
        this.matchService = matchService;
        this.userService = userService;
        this.questionRepository = questionRepository;
    }



    // 🔥 Start match
    @PostMapping("/{matchId}/start")
    public Match startMatch(
            @PathVariable UUID matchId
    ) {

        return matchService.startMatch(matchId);
    }



    // 🔥 Submit answer
    @PostMapping("/{matchId}/answer")
    public Match submitAnswer(
            @PathVariable UUID matchId,
            @RequestParam UUID questionId,
            @RequestParam String answer,
            @RequestParam Long timeTakenMs
    ) {

        User user = userService.getCurrentUser();

        Question question =
                questionRepository
                        .findById(questionId)
                        .orElseThrow();


        return matchService.submitAnswer(
                matchId,
                user,
                question,
                answer,
                timeTakenMs
        );
    }



    // 🔥 Get match details
    @GetMapping("/{matchId}")
    public Match getMatch(
            @PathVariable UUID matchId
    ) {

        return matchService
                .startMatch(matchId); // simple fetch (can improve later)
    }

}
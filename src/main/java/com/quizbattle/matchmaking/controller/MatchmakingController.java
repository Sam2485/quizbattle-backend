package com.quizbattle.matchmaking.controller;

import com.quizbattle.match.entity.Match;

import com.quizbattle.matchmaking.service.MatchmakingService;

import com.quizbattle.subject.entity.Subject;
import com.quizbattle.subject.repository.SubjectRepository;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;



@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    private final UserService userService;

    private final SubjectRepository subjectRepository;



    public MatchmakingController(
            MatchmakingService matchmakingService,
            UserService userService,
            SubjectRepository subjectRepository
    ) {
        this.matchmakingService = matchmakingService;
        this.userService = userService;
        this.subjectRepository = subjectRepository;
    }



    @PostMapping("/join/{subjectId}")
    public String joinQueue(
            @PathVariable UUID subjectId
    ) {

        User user =
                userService.getCurrentUser();


        Subject subject =
                subjectRepository
                        .findById(subjectId)
                        .orElseThrow();


        matchmakingService.joinQueue(
                user,
                subject
        );


        return "Joined matchmaking queue";

    }



    @GetMapping("/candidates/{subjectId}")
    public Set<String> findCandidates(
            @PathVariable UUID subjectId,
            @RequestParam int minRating,
            @RequestParam int maxRating
    ) {

        Subject subject =
                subjectRepository
                        .findById(subjectId)
                        .orElseThrow();


        return matchmakingService.findCandidates(
                subject,
                minRating,
                maxRating
        );

    }



    @PostMapping("/match/{subjectId}/{opponentId}")
    public Match createMatch(
            @PathVariable UUID subjectId,
            @PathVariable UUID opponentId
    ) {

        User player =
                userService.getCurrentUser();


        Subject subject =
                subjectRepository
                        .findById(subjectId)
                        .orElseThrow();


        User opponent = new User();
        opponent.setFirebaseUid(opponentId.toString());


        return matchmakingService.tryMatch(
                player,
                subject,
                opponent
        );

    }

}
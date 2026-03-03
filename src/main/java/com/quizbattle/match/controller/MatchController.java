package com.quizbattle.match.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.match.dto.MatchResponseDTO;
import com.quizbattle.match.dto.SubmitAnswerRequestDTO;
import com.quizbattle.match.entity.Match;
import com.quizbattle.match.service.MatchService;

import com.quizbattle.question.entity.Question;
import com.quizbattle.question.repository.QuestionRepository;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import jakarta.validation.Valid;

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



    @PostMapping("/{matchId}/start")
    public ApiResponse<MatchResponseDTO> startMatch(
            @PathVariable UUID matchId
    ) {

        Match match = matchService.startMatch(matchId);

        return ApiResponse.success(toDTO(match));
    }



    @PostMapping("/{matchId}/answer")
    public ApiResponse<MatchResponseDTO> submitAnswer(
            @PathVariable UUID matchId,
            @Valid @RequestBody SubmitAnswerRequestDTO request
    ) {

        User user = userService.getCurrentUser();

        Question question =
                questionRepository
                        .findById(request.getQuestionId())
                        .orElseThrow();

        Match match =
                matchService.submitAnswer(
                        matchId,
                        user,
                        question,
                        request.getAnswer(),
                        request.getTimeTakenMs()
                );

        return ApiResponse.success(toDTO(match));
    }



    @GetMapping("/{matchId}")
    public MatchResponseDTO getMatch(
            @PathVariable UUID matchId
    ) {

        Match match =
                matchService.startMatch(matchId);

        return toDTO(match);
    }



    private MatchResponseDTO toDTO(Match match) {

        return new MatchResponseDTO(
                match.getId(),
                match.getPlayer1().getId(),
                match.getPlayer2().getId(),
                match.getWinner() != null
                        ? match.getWinner().getId()
                        : null,
                match.getStatus(),
                match.getStartedAt(),
                match.getFinishedAt()
        );
    }

}
package com.quizbattle.matchmaking.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.match.entity.Match;
import com.quizbattle.matchmaking.dto.MatchmakingResponseDTO;
import com.quizbattle.matchmaking.service.MatchmakingService;
import com.quizbattle.subject.entity.Subject;
import com.quizbattle.subject.service.SubjectService;
import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;
    private final UserService userService;
    private final SubjectService subjectService;



    public MatchmakingController(
            MatchmakingService matchmakingService,
            UserService userService,
            SubjectService subjectService
    ) {
        this.matchmakingService = matchmakingService;
        this.userService = userService;
        this.subjectService = subjectService;
    }



    @PostMapping("/join/{subjectId}")
    public ApiResponse<MatchmakingResponseDTO> joinQueue(
            @PathVariable UUID subjectId
    ) {

        User user = userService.getCurrentUser();

        Subject subject =
                subjectService.getSubject(subjectId);

        Match match =
                matchmakingService.joinQueue(user, subject);

        if (match == null) {

            return ApiResponse.success(
                    new MatchmakingResponseDTO(
                            "WAITING",
                            null
                    )
            );
        }

        return ApiResponse.success(
                new MatchmakingResponseDTO(
                        "MATCH_FOUND",
                        match.getId()
                )
        );
    }

}
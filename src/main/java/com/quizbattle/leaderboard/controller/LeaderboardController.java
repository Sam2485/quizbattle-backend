package com.quizbattle.leaderboard.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.leaderboard.dto.LeaderboardEntryDTO;
import com.quizbattle.leaderboard.service.LeaderboardService;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;



@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final UserService userService;



    public LeaderboardController(
            LeaderboardService leaderboardService,
            UserService userService
    ) {
        this.leaderboardService = leaderboardService;
        this.userService = userService;
    }



    @GetMapping("/{subjectId}")
    public ApiResponse<List<LeaderboardEntryDTO>> getLeaderboard(
            @PathVariable UUID subjectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ApiResponse.success(
                leaderboardService.getLeaderboardPage(
                        subjectId,
                        page,
                        size
                )
        );
    }



    // 🔥 Get current user rank
    @GetMapping("/{subjectId}/rank")
    public ApiResponse<Long> getMyRank(
            @PathVariable UUID subjectId
    ) {

        User user = userService.getCurrentUser();

        Long rank = leaderboardService
                .getRank(subjectId, user.getId());

        return ApiResponse.success(rank);
    }

}
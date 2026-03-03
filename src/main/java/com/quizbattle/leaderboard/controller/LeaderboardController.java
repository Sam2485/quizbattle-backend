package com.quizbattle.leaderboard.controller;

import com.quizbattle.leaderboard.service.LeaderboardService;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

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



    // 🔥 Get top N players for subject
    @GetMapping("/{subjectId}/top")
    public Set<String> getTopPlayers(
            @PathVariable UUID subjectId,
            @RequestParam(defaultValue = "10") int limit
    ) {

        return leaderboardService
                .getTopPlayers(subjectId, limit);
    }



    // 🔥 Get current user rank
    @GetMapping("/{subjectId}/rank")
    public Long getMyRank(
            @PathVariable UUID subjectId
    ) {

        User user = userService.getCurrentUser();

        return leaderboardService
                .getRank(subjectId, user.getId());
    }

}
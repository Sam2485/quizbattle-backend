package com.quizbattle.rating.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.rating.dto.RatingResponseDTO;
import com.quizbattle.rating.entity.Rating;
import com.quizbattle.rating.service.RatingService;
import com.quizbattle.subject.entity.Subject;
import com.quizbattle.subject.service.SubjectService;
import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final SubjectService subjectService;



    public RatingController(
            RatingService ratingService,
            UserService userService,
            SubjectService subjectService
    ) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.subjectService = subjectService;
    }



    @GetMapping("/{subjectId}")
    public ApiResponse<RatingResponseDTO> getMyRating(
            @PathVariable UUID subjectId
    ) {

        User user = userService.getCurrentUser();

        Subject subject =
                subjectService.getSubject(subjectId);

        Rating rating =
                ratingService.getOrCreateRating(user, subject);

        RatingResponseDTO dto =
                new RatingResponseDTO(
                        subjectId,
                        rating.getRating(),
                        rating.getMatchesPlayed()
                );

        return ApiResponse.success(dto);
    }

}
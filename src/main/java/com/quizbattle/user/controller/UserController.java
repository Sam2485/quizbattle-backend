package com.quizbattle.user.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.user.dto.UserResponseDTO;
import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }



    @GetMapping("/me")
    public ApiResponse<UserResponseDTO> getCurrentUser() {

        User user = userService.getCurrentUser();

        return ApiResponse.success(toDTO(user));
    }



    private UserResponseDTO toDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getFirebaseUid(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt(),
                user.getLastActive()
        );
    }

}
package com.quizbattle.friend.controller;

import com.quizbattle.common.dto.ApiResponse;
import com.quizbattle.friend.dto.FriendResponseDTO;
import com.quizbattle.friend.entity.Friend;
import com.quizbattle.friend.service.FriendService;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;



    public FriendController(
            FriendService friendService,
            UserService userService
    ) {
        this.friendService = friendService;
        this.userService = userService;
    }



    // 🔥 Send friend request
    @PostMapping("/request/{receiverId}")
    public ApiResponse<FriendResponseDTO> sendFriendRequest(
            @PathVariable UUID receiverId
    ) {

        User currentUser = userService.getCurrentUser();

        Friend friend =
                friendService.sendFriendRequest(currentUser, receiverId);

        return ApiResponse.success(toDTO(friend));
    }



    // 🔥 Accept request
    @PostMapping("/accept/{requestId}")
    public ApiResponse<FriendResponseDTO> acceptRequest(
            @PathVariable UUID requestId
    ) {

        User currentUser = userService.getCurrentUser();

        Friend friend =
                friendService.acceptFriendRequest(requestId, currentUser);

        return ApiResponse.success(toDTO(friend));
    }



    // 🔥 Reject request
    @PostMapping("/reject/{requestId}")
    public ApiResponse<FriendResponseDTO> rejectRequest(
            @PathVariable UUID requestId
    ) {

        User currentUser = userService.getCurrentUser();

        Friend friend =
                friendService.rejectFriendRequest(requestId, currentUser);

        return ApiResponse.success(toDTO(friend));
    }



    // 🔥 List friends
    @GetMapping
    public ApiResponse<List<FriendResponseDTO>> listFriends() {

        User currentUser = userService.getCurrentUser();

        List<FriendResponseDTO> result =
                friendService.listFriends(currentUser)
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ApiResponse.success(result);
    }

    private FriendResponseDTO toDTO(Friend friend) {
        return new FriendResponseDTO(
                friend.getId(),
                friend.getSender().getId(),
                friend.getReceiver().getId(),
                friend.getStatus(),
                friend.getCreatedAt()
        );
    }

}
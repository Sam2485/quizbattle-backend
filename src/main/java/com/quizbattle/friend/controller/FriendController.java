package com.quizbattle.friend.controller;

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
    public Friend sendFriendRequest(
            @PathVariable UUID receiverId
    ) {

        User currentUser =
                userService.getCurrentUser();

        return friendService
                .sendFriendRequest(currentUser, receiverId);
    }



    // 🔥 Accept request
    @PostMapping("/accept/{requestId}")
    public Friend acceptRequest(
            @PathVariable UUID requestId
    ) {

        User currentUser =
                userService.getCurrentUser();

        return friendService
                .acceptFriendRequest(requestId, currentUser);
    }



    // 🔥 Reject request
    @PostMapping("/reject/{requestId}")
    public Friend rejectRequest(
            @PathVariable UUID requestId
    ) {

        User currentUser =
                userService.getCurrentUser();

        return friendService
                .rejectFriendRequest(requestId, currentUser);
    }



    // 🔥 List friends
    @GetMapping
    public List<Friend> listFriends() {

        User currentUser =
                userService.getCurrentUser();

        return friendService.listFriends(currentUser);
    }

}
package com.quizbattle.friend.service;

import com.quizbattle.friend.entity.Friend;
import com.quizbattle.friend.repository.FriendRepository;

import com.quizbattle.user.entity.User;
import com.quizbattle.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;



@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;



    public FriendService(
            FriendRepository friendRepository,
            UserRepository userRepository
    ) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }



    @Transactional
    public Friend sendFriendRequest(
            User sender,
            UUID receiverId
    ) {

        User receiver =
                userRepository
                        .findById(receiverId)
                        .orElseThrow();


        if (friendRepository
                .findBySenderAndReceiver(sender, receiver)
                .isPresent()) {

            throw new RuntimeException("Friend request already exists");
        }


        Friend friend =
                new Friend(sender, receiver);

        return friendRepository.save(friend);
    }



    @Transactional
    public Friend acceptFriendRequest(
            UUID friendRequestId,
            User currentUser
    ) {

        Friend friend =
                friendRepository
                        .findById(friendRequestId)
                        .orElseThrow();


        if (!friend.getReceiver()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException("Not authorized");
        }


        friend.setStatus("ACCEPTED");

        return friendRepository.save(friend);
    }



    @Transactional
    public Friend rejectFriendRequest(
            UUID friendRequestId,
            User currentUser
    ) {

        Friend friend =
                friendRepository
                        .findById(friendRequestId)
                        .orElseThrow();


        if (!friend.getReceiver()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException("Not authorized");
        }


        friend.setStatus("REJECTED");

        return friendRepository.save(friend);
    }



    public List<Friend> listFriends(User user) {

        return friendRepository
                .findBySenderOrReceiver(user, user);
    }

}
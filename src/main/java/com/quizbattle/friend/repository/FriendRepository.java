package com.quizbattle.friend.repository;

import com.quizbattle.friend.entity.Friend;
import com.quizbattle.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FriendRepository
        extends JpaRepository<Friend, UUID> {

    Optional<Friend> findBySenderAndReceiver(
            User sender,
            User receiver
    );



    List<Friend> findBySenderOrReceiver(
            User sender,
            User receiver
    );

}
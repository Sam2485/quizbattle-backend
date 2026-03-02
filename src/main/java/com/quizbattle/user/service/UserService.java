package com.quizbattle.user.service;

import com.quizbattle.security.FirebaseUser;
import com.quizbattle.user.entity.User;
import com.quizbattle.user.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;



@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public User getOrCreateUser(FirebaseUser firebaseUser) {

        Optional<User> existing =
                userRepository.findByFirebaseUid(firebaseUser.getUid());


        if (existing.isPresent()) {

            User user = existing.get();

            user.setLastActive(Instant.now());

            return userRepository.save(user);

        }


        User newUser = new User(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                firebaseUser.getName()
        );

        return userRepository.save(newUser);

    }



    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new RuntimeException("No authenticated user");
        }

        FirebaseUser firebaseUser =
                (FirebaseUser) authentication.getPrincipal();

        return userRepository
                .findByFirebaseUid(firebaseUser.getUid())
                .orElseThrow(() ->
                        new RuntimeException("User not found in database"));
    }

}
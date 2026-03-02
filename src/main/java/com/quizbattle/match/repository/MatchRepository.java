package com.quizbattle.match.repository;

import com.quizbattle.match.entity.Match;
import com.quizbattle.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;



public interface MatchRepository extends JpaRepository<Match, UUID> {

    List<Match> findByPlayer1OrPlayer2(User player1, User player2);

}
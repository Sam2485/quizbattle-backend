package com.quizbattle.match.repository;

import com.quizbattle.match.entity.Match;
import com.quizbattle.match.entity.MatchAnswer;
import com.quizbattle.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;



public interface MatchAnswerRepository
        extends JpaRepository<MatchAnswer, UUID> {



    List<MatchAnswer> findByMatchAndUser(
            Match match,
            User user
    );



    @Query("""
            SELECT COUNT(ma)
            FROM MatchAnswer ma
            WHERE ma.match = :match
            AND ma.user = :user
            AND ma.isCorrect = true
            """)
    Long countCorrectAnswers(
            @Param("match") Match match,
            @Param("user") User user
    );



    @Query("""
            SELECT COALESCE(SUM(ma.timeTakenMs), 0)
            FROM MatchAnswer ma
            WHERE ma.match = :match
            AND ma.user = :user
            """)
    Long totalTimeTaken(
            @Param("match") Match match,
            @Param("user") User user
    );

}
package com.quizbattle.rating.repository;

import com.quizbattle.rating.entity.Rating;
import com.quizbattle.user.entity.User;
import com.quizbattle.subject.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface RatingRepository extends JpaRepository<Rating, UUID> {

    Optional<Rating> findByUserAndSubject(User user, Subject subject);


    List<Rating> findBySubjectOrderByRatingDesc(Subject subject);

}
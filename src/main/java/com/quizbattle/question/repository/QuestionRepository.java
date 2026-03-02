package com.quizbattle.question.repository;

import com.quizbattle.question.entity.Question;
import com.quizbattle.subject.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;



public interface QuestionRepository extends JpaRepository<Question, UUID> {



    List<Question> findBySubjectAndDifficultyLevel(
            Subject subject,
            String difficultyLevel
    );



    @Query(
            value =
                    """
                    SELECT *
                    FROM questions
                    WHERE subject_id = :subjectId
                    AND difficulty_level = :difficulty
                    ORDER BY RANDOM()
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<Question> findRandomQuestions(
            @Param("subjectId") UUID subjectId,
            @Param("difficulty") String difficulty,
            @Param("limit") int limit
    );

}
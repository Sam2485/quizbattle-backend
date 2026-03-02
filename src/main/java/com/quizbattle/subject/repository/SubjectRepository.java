package com.quizbattle.subject.repository;

import com.quizbattle.subject.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;



public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    Optional<Subject> findByCode(String code);

}
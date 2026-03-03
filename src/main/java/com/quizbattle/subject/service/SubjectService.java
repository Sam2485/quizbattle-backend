package com.quizbattle.subject.service;

import com.quizbattle.subject.entity.Subject;
import com.quizbattle.subject.repository.SubjectRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Cacheable("subjects")
    public Subject getSubject(UUID id) {
        return subjectRepository.findById(id).orElseThrow();
    }
}
package com.kkoma.boot.jpa.querydsl.service;

import com.kkoma.boot.jpa.querydsl.entity.SampleEntity;
import com.kkoma.boot.jpa.querydsl.repository.SampleRepository;
import com.kkoma.boot.jpa.querydsl.repository.SampleRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    final SampleRepository sampleRepository;
    final SampleRepositorySupport sampleRepositorySupport;

    public Page<SampleEntity> getSamples(Pageable pageable) {
        return sampleRepository.findAll(pageable);
    }

    public Page<SampleEntity> findByName(Pageable pageable, String name) {
        return sampleRepositorySupport.findByName(pageable, name);
    }
}

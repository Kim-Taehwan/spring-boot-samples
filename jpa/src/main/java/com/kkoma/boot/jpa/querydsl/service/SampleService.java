package com.kkoma.boot.jpa.querydsl.service;

import com.kkoma.boot.jpa.querydsl.entity.SampleEntity;
import com.kkoma.boot.jpa.querydsl.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    final SampleRepository sampleRepository;

    public Page<SampleEntity> getSamples(Pageable pageable) {
        return sampleRepository.findAll(pageable);
    }
}

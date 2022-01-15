package com.kkoma.boot.jpa.querydsl.repository;

import com.kkoma.boot.jpa.querydsl.entity.SampleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends PagingAndSortingRepository<SampleEntity, Long> {
}

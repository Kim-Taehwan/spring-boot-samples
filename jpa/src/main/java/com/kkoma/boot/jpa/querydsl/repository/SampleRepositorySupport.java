package com.kkoma.boot.jpa.querydsl.repository;

import com.kkoma.boot.jpa.querydsl.entity.SampleEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kkoma.boot.jpa.querydsl.entity.QSampleEntity.sampleEntity;

@Slf4j
@Repository
public class SampleRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public SampleRepositorySupport(JPAQueryFactory queryFactory) {
        super(SampleEntity.class);
        this.queryFactory = queryFactory;
    }

    public Page<SampleEntity> findByName(Pageable pageable, String name) {
        List<SampleEntity> results = queryFactory
                .selectFrom(sampleEntity)
                .where(sampleEntity.name.eq(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("results: {}", results);
        return new PageImpl<>(results, pageable, results.size());
    }
}

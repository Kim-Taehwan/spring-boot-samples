package com.example.querydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.example.querydsl.entity.QMember.*;
import static com.example.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class MemberTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team(1L, "teamA");
        Team teamB = new Team(2L, "teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member(1L, "member1", 20, teamA );
        Member member2 = new Member(2L, "member2", 10, teamA );
        Member member3 = new Member(3L, "member3", 20, teamB );
        Member member4 = new Member(4L, "member4", 20, teamB );
        Member member5 = new Member(5L, null, 20, teamB );
        Member member6 = new Member(6L, "member6", 20, teamB );
        Member member7 = new Member(7L, "member7", 20, teamB );

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(member7);
    }

    @Test
    public void startJPQL() {
        // member1 을 찾아라.
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        log.info("findMember: {}", findMember.toString());
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        QMember m = new QMember("m");

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member2")) // 파라미터 바인딩 처리
                .fetchOne();

        log.info("findMember: {}", findMember.toString());
        assertThat(findMember.getUsername()).isEqualTo("member2");

    }

    @Test
    @DisplayName("static import 사용")
    public void startQuerydsl2() {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member3")) // 파라미터 바인딩 처리
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member3");
    }

    @Test
    @DisplayName("검색 조건 쿼리")
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member2"),
                        (member.age.eq(10)))
                .fetchOne();

        log.info("findMember: {}", findMember.toString());
        assertThat(findMember.getUsername()).isEqualTo("member2");
    }

    @Test
    @DisplayName("결과 조회")
    public void searchResult() {
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        log.info("fetch: {}", fetch.toString());

        Member findMember1 = queryFactory
                .selectFrom(member)
                .fetchFirst();

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();

        log.info("results: {}", results.getTotal());
        assertThat(results.getTotal()).isEqualTo(4);
    }

    @Test
    @DisplayName("정렬")
    public void sort() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(20))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        log.info("result: {}", result.toString());
    }

    @Test
    @DisplayName("페이징")
    public void paging() {
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        log.info("fetch: {}", fetch.size());
    }
}
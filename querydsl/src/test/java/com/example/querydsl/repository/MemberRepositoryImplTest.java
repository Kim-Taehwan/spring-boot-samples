package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
public class MemberRepositoryImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team(11L, "teamA");
        Team teamB = new Team(21L, "teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member(111L, "member1", 20, teamA );
        Member member2 = new Member(211L, "member2", 10, teamA );
        Member member3 = new Member(311L, "member3", 20, teamB );
        Member member4 = new Member(411L, "member4", 10, teamB );
        Member member5 = new Member(511L, null, 10, teamB );
        Member member6 = new Member(611L, "member6", 30, teamB );
        Member member7 = new Member(711L, "member7", 40, teamB );

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(member7);
    }

    @Test
    public void searchTestSimple() throws Exception {

        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setAgeGoe(35);
//        condition.setAgeGoe(40);
//        condition.setUsername("member1");
//        condition.setTeamName("teamB");

        final PageRequest page = PageRequest.of(0, 3);

        final Page<MemberTeamDto> results = memberRepository.searchPageSimpleComplex(condition, page);

        for (MemberTeamDto memberTeamDto : results) {
            log.info("memberTeamDto: {}", memberTeamDto.toString());
        }
    }
}
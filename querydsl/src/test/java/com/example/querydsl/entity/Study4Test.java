package com.example.querydsl.entity;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.repository.MemberJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.example.querydsl.entity.QMember.member;
import static com.example.querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class Study4Test {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @BeforeEach
    public void beforeEach() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team(1L, "teamA");
        Team teamB = new Team(2L, "teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member(1L, "member1", 20, teamA );
        Member member2 = new Member(2L, "member2", 10, teamA );
        Member member3 = new Member(3L, "member3", 20, teamB );
        Member member4 = new Member(4L, "member4", 10, teamB );
        Member member5 = new Member(5L, null, 10, teamB );
        Member member6 = new Member(6L, "member6", 30, teamB );
        Member member7 = new Member(7L, "member7", 40, teamB );

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(member7);
    }

    @Test
    public void jpaRepository() {
        log.info("jpaRepository()");
        final List<Member> result = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .fetch();

        for (Member member1 : result) {
            log.info("result: {}", member1.getTeam().getName());
        }
    }

    @Test
    public void searchTest() throws Exception {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeGoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        for (MemberTeamDto memberTeamDto : result) {
            log.info("memberTeamDto: {}", memberTeamDto.toString());
        }
    }

    @Test
    public void searchTest2() throws Exception {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeGoe(40);
        condition.setTeamName("teamB");

        final List<MemberTeamDto> search = memberJpaRepository.search(condition);
        for (MemberTeamDto memberTeamDto : search) {
            log.info("memberTeamDto: {}", memberTeamDto);
        }
        assertThat(search).extracting("username").containsExactly("member7");
    }

}

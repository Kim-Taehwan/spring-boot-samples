package com.example.querydsl.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
public class MemberTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        Team teamA = new Team(1L, "teamA");
        Team teamB = new Team(2L, "teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member(1L, "member1", 20, teamA );
        Member member2 = new Member(2L, "member2", 20, teamA );
        Member member3 = new Member(3L, "member3", 20, teamB );
        Member member4 = new Member(4L, "member4", 20, teamB );

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
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

    }
}
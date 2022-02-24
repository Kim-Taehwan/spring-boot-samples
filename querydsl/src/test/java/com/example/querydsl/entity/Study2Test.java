package com.example.querydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.querydsl.entity.QTeam.team;

@Slf4j
@SpringBootTest
@Transactional
public class Study2Test {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team(1L, "teamA");
        Team teamB = new Team(2L, "teamB");

        em.persist(teamA);
    }

    @Test
    public void first() {
        log.info("first");

        final List<Team> result = queryFactory
                .selectFrom(team)
                .where(team.name.eq("teamA"))
                .fetch();

        for(Team t : result) {
            log.info("result: {}", t.toString());
        }
    }
}

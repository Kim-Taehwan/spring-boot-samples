package com.example.querydsl.api;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Team teamA = new Team(3L, "teamA");
            Team teamB = new Team(4L, "teamB");

            em.persist(teamA);
            em.persist(teamB);

            for (int i = 6; i < 100; i++) {
                Team selectedTeam = i % 2 == 0 ? teamA : teamB;
                em.persist(new Member(Integer.toUnsignedLong(i), "member" +i, i, selectedTeam));
            }
        }
    }
}

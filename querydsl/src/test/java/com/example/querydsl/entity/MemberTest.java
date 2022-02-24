package com.example.querydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import java.util.List;

import static com.example.querydsl.entity.QMember.*;
import static com.example.querydsl.entity.QMember.member;
import static com.example.querydsl.entity.QTeam.*;
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
        Member member4 = new Member(4L, "member4", 10, teamB );
        Member member5 = new Member(5L, null, 10, teamB );
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

    @Test
    @DisplayName("집합")
    public void grouping() {
        final List<Tuple> fetch = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = fetch.get(0);
        log.info("tuple: {}", tuple.toString());
        assertThat(tuple.get(member.count())).isEqualTo(7);
        assertThat(tuple.get(member.age.sum())).isEqualTo(130);
        assertThat(tuple.get(member.age.avg())).isEqualTo(18.0);
        assertThat(tuple.get(member.age.max())).isEqualTo(20);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    @Test
    @DisplayName("팀의 이름과 각 팀의 평균 연령을 구해라.")
    public void groupby() throws Exception {
        final List<Tuple> fetch = queryFactory
                .select(
                        team.name,
                        member.age.avg()
                )
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = fetch.get(0);
        Tuple teamB = fetch.get(1);

        log.info("teamA: {}", teamA);
        log.info("teamB: {}", teamB);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
    }

    @Test
    @DisplayName("팀 A 에 소속된 모든 회")
    public void joinBasic() throws Exception {
        final List<Member> teamA = queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        log.info("teamA: {}", teamA.toString());

        assertThat(teamA)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    @DisplayName("회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회")
    public void join_on_filtering() throws Exception {
        final List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        log.info("result: {}", result.toString());
    }

    @Test
    @DisplayName("연관관계 없는 엔티티 외부 조인")
    public void join_on_no_relation() throws Exception {
        final List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            log.info("tuple = " + tuple);
        }
    }

    // EntityManagerFactory
    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() {
        em.flush();
        em.clear();

        final Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // findMember.getTeam() 가 이미 로딩된 것인지 알 수 있음. 로딩 됐으면, true
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("fetch 조인 미적용").isFalse();
    }

    @Test
    public void fetchJoinYes() throws Exception {
        em.flush();
        em.clear();

        final Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        // findMember.getTeam() 가 이미 로딩된 것인지 알 수 있음. 로딩 됐으면, true
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("fetch 조인 미적용").isFalse();
    }

    @Test
    @DisplayName("나이가 가장 많은 회원 조회")
    public void subQuery() {
        QMember memberSub = new QMember("memberSub");

        final List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions.select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(40);
    }

    @Test
    public void basicCase() throws Exception {
        final List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("ETC"))
                .from(member)
                .fetch();

        for (String s : result) {
            log.info("s = {}", s);
        }
    }

    @Test
    public void complexCase() throws Exception {
        final List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 10)).then("0~20살")
                        .when(member.age.between(11, 20)).then("11~20살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        for (String s : result) {
            log.info("s = {}", s);
        }
    }

    @Test
    public void constant() throws Exception {
        final List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            log.info("tuple: {}", tuple);
        }
    }

    @Test
    public void concat() throws Exception {
        final List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("mamber1"))
                .fetch();

        for (String s : result) {
            log.info("s: {} ", s);
        }
    }
}
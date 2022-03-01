package com.example.querydsl.entity;

import com.example.querydsl.dto.MemberDto;
import com.example.querydsl.dto.QMemberDto;
import com.example.querydsl.dto.UserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void simpleProjection() {
        log.info("first");

        final List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for(String t : result) {
            log.info("result: {}", t);
        }
    }

    @Test
    public void tupleProjection() throws Exception {
        final List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            log.info("username: {}", username);
            log.info("age: {}", age);
        }
    }

    @Test
    public void findDtoByJPQL() {
        final List<MemberDto> resultList = em.createQuery("select new com.example.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : resultList) {
            log.info("memberDto: {}", memberDto);
        }

    }

    @Test
    public void findBySetter() throws Exception {
        final List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            log.info("memberDto: {}", memberDto.toString());
        }
    }
    
    @Test
    public void findDtoByField() throws Exception {
        final List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            log.info("memberDto: {}", memberDto.toString());
        }
    }

    @Test
    public void findDtoByConstructor() throws Exception {
        final List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            log.info("memberDto: {}", memberDto.toString());
        }
    }

    @Test
    public void findUserDto() throws Exception {

        final QMember memberSub = new QMember("memberSub");

        final List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),
                        ExpressionUtils.as(JPAExpressions
                        .select(memberSub.age.max()).from(memberSub), "age")
                ))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            log.info("UserDto: {}", userDto);
        }
    }

    @Test
    public void findDtoByQueryProjection() {
        final List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            log.info("memberDto: {}", memberDto);
        }
    }

    @Test
    public void dynamicQuery_BooleanBuilder() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 20;

        List<Member> result = searchMember1(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void 동적쿼리_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 20;

        List<Member> result = searchMember2(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {

        if(usernameCond == null) {
            return null;
        }

        return member.username.eq(usernameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {

        if (ageCond == null) {
            return null;
        }

        return member.age.eq(ageCond);
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    @Commit
    public void bulkUpdate() throws Exception {
        final long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(20))
                .execute();

        em.flush();
        em.clear();

        final List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        log.info("result: {}", result);
        log.info("count: {}", count);
    }
    
    @Test
    public void bulkDelete() throws Exception {
        final long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();

        em.flush();
        em.clear();

        final List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        log.info("result: {}", result);
        log.info("count: {}", count);
    }

    @Test
    public void sqlFunction() throws Exception {
        final List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            log.info("s: {}", s);
        }
    }
}

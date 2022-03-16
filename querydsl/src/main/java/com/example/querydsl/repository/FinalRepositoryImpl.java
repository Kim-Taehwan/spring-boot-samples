package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsl.entity.QMember.member;
import static com.example.querydsl.entity.QTeam.team;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.quote;

@Repository
public class FinalRepositoryImpl extends FinalRepositorySupport {

    public FinalRepositoryImpl() {
        super(Member.class);
    }

    public List<Member> basicSelect() {
        return select(member)
                .from(member)
                .fetch();
    }

    public List<Member> basicSelectFrom() {
        return selectFrom(member)
                .fetch();
    }

    public Page<Member> searchPageByApplyPage(MemberSearchCondition condition, Pageable pageable) {

        return null;
    }

    public Page<Member> applyPagination(MemberSearchCondition condition, Pageable pageable) {

        return null;
    }


    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression usernameEq(String username) {
        if (hasText(username)) {
            return member.username.eq(username);
        }

        return null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return (ageGoe == null) ? null : member.age.goe(ageGoe);
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return (ageLoe == null) ? null : member.age.loe(ageLoe);
    }

}

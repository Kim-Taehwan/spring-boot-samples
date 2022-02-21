package com.example.querydsl.utils.querydsl;

import com.querydsl.core.types.dsl.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class QuerydslCustom {

    public <T> BooleanExpression eq (SimpleExpression<T> expression, T right) {
        if (right == null) {
            return null;
        }

        return expression.eq(right);
    }

    public <T> BooleanExpression ne (SimpleExpression<T> expression, T right) {
        if (right == null) {
            return null;
        }

        return expression.ne(right);
    }

    public BooleanExpression like (StringExpression expression, String right) {
        if (!StringUtils.hasText(right)) {
            return null;
        }

        return expression.like("%" + right + "%");
    }

    public <T> BooleanExpression in (SimpleExpression<T> expression, T... right) {
        return in(expression, Arrays.asList(right));
    }

    public <T> BooleanExpression in (SimpleExpression<T> expression, List<T> right) {
        if (right == null || right.isEmpty()) {
            return null;
        }

        return expression.in(right);
    }

    public <T extends Comparable<?>> BooleanExpression between (
            ComparableExpression<T> expression, T from, T to) {
        if (from == null || to == null) {
            return null;
        }

        return expression.between(from, to);
    }

    public <T extends Number & Comparable<?>> BooleanExpression goe (
            NumberExpression<T> expression, T right ) {
        if (right == null) {
            return null;
        }

        return expression.goe(right);
    }
}

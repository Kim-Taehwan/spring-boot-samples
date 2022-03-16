package com.example.querydsl.repository;

import com.example.querydsl.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
class FinalRepositoryImplTest {

    @Autowired
    private FinalRepositoryImpl finalRepository;

    @Test
    public void basicTest() {

        List<Member> actual = finalRepository.basicSelect();

        log.info("actual: {}", actual);
    }
}
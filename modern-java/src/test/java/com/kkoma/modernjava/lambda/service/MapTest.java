package com.kkoma.modernjava.lambda.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class MapTest {

    List<String> resultList = new ArrayList<>();
    List<Long> purchaseProductIdList = new ArrayList<>();

    @Test
    public void mapTest() {
        purchaseProductIdList.add(1L);
        purchaseProductIdList.add(122L);
        purchaseProductIdList.add(133L);

        StringBuilder builder = new StringBuilder();
        builder.append("취소된 주문 매입번호: \n");
        for (Long id : purchaseProductIdList) {
            builder.append(id + "\n");
        }

        resultList.add(builder.toString());

        log.info("builder: {}", builder.toString());
    }
}

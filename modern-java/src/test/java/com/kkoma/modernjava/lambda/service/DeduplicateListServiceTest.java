package com.kkoma.modernjava.lambda.service;

import com.kkoma.modernjava.lambda.utils.StreamUtil;
import com.kkoma.modernjava.lambda.vo.OrderCancel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
public class DeduplicateListServiceTest {

    static List<OrderCancel> orderCancels = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        OrderCancel orderCancel1 = OrderCancel.all()
                .orderId(1L)
                .reason("Good")
                .build();

        OrderCancel orderCancel2 = OrderCancel.all()
                .orderId(2L)
                .reason("Bad")
                .build();

        orderCancels.add(orderCancel1);
        orderCancels.add(orderCancel1);
        orderCancels.add(orderCancel1);
        orderCancels.add(orderCancel1);
        orderCancels.add(orderCancel2);
    }

    @Test
    @DisplayName("List 에서 중복 order Id 제거")
    public void deduplicateList() {
        List<OrderCancel> orderCancelList = StreamUtil.deduplicate(orderCancels, OrderCancel::getOrderId);

        assertEquals(2, orderCancelList.size());
    }

    @Test
    @DisplayName("List에서 중복인 order Id는 완전히 제거")
    public void deduplicateList02() {
        Set<Long> orderIdSet = orderCancels.stream()
                .map(OrderCancel::getOrderId)
                .collect(Collectors.toSet());

        assertEquals(2, orderIdSet.size());

        // 2. set을 순회하면서, list 에 2개 이상있을 경우, list 에서 제거
        List<OrderCancel> duplicatedList = new ArrayList<>();
        Iterator<Long> iter = orderIdSet.iterator();
        while (iter.hasNext()) {
            Long orderId = iter.next();
            Long count = orderCancels.stream()
                    .filter(o -> o.getOrderId().equals(orderId))
                    .count();

            if (count > 1) {
                duplicatedList.addAll(orderCancels.stream()
                        .filter(o -> o.getOrderId().equals(orderId))
                        .collect(Collectors.toList()));
            }
        }

        orderCancels.removeAll(duplicatedList);
        assertEquals(1, orderCancels.size());
    }
}
package com.kkoma.boot.jpa.order.service;

import com.kkoma.boot.jpa.order.dto.OrderCancelResponse;
import com.kkoma.boot.jpa.order.entity.Order;
import com.kkoma.boot.jpa.order.repository.OrderRepository;
import com.kkoma.boot.jpa.order.vo.CancelParam;
import com.kkoma.boot.jpa.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderCancelResponse getOrderCancelResponse(CancelParam cancelParam) {
        Long orderId = cancelParam.getOrderId();

        Order order = getOrderById(orderId);
        return null;
    }

    private Order getOrderById(Long orderId) {
        Order order = orderRepository.findOneById(orderId).orElse(null);
        if (Objects.nonNull(order)) {
            return order;
        }

        //
        return null;
    }

}

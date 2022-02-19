package com.kkoma.boot.jpa.search.service;

import com.kkoma.boot.jpa.order.entity.Order;
import com.kkoma.boot.jpa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final OrderService orderService;


}

package com.kkoma.boot.jpa.order.repository;

import com.kkoma.boot.jpa.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOneById(Long orderId);
}

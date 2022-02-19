package com.kkoma.boot.jpa.order.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GENERATOR")
    @SequenceGenerator(name = "ORDERS_SEQ_GENERATOR", allocationSize = 1, sequenceName = "ORDERS_SEQ")
    private Long id;
    private Long userId;
    private int status;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String ordererMobile;
    private int usedEmoneyAmount;
    private int usedCouponAmount;
    private Date orderedDate;
    private Date paidDate;
    private Date registeredDate;
    private Date modifiedDate;
}

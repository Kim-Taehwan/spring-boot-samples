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

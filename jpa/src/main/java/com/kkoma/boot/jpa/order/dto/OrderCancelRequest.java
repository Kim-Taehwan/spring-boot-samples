package com.kkoma.boot.jpa.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(builderClassName = "All", builderMethodName = "all")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCancelRequest {
    private List<Long> ids;
    private int code;
}

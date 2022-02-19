package com.kkoma.boot.jpa.order.vo;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderClassName = "All", builderMethodName = "all")
public class CancelParam {
    private Long orderId;
}

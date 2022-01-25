package com.kkoma.modernjava.lambda.vo;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderClassName = "All", builderMethodName = "all")
public class OrderCancel {
    private String cancelId;
    private Integer code;
    private String reason;
    private Integer refundPrice;
    private Integer refundFee;
    private Integer refundEmoney;
    private Integer refundDeliveryFee;
    private Long orderId;
}

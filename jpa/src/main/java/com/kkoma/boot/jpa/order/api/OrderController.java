package com.kkoma.boot.jpa.order.api;

import com.kkoma.boot.jpa.order.constants.RestApiConstants;
import com.kkoma.boot.jpa.order.dto.OrderCancelRequest;
import com.kkoma.boot.jpa.order.dto.OrderCancelResponse;
import com.kkoma.boot.jpa.order.entity.Order;
import com.kkoma.boot.jpa.order.service.OrderService;
import com.kkoma.boot.jpa.order.vo.CancelParam;
import com.kkoma.boot.jpa.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = RestApiConstants.ORDER_ENDPOINT)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
                                      @RequestBody OrderCancelRequest request) {

        /**
         * TokenDetails tokenDetails = authenticateDetailService.getTokenDetails();
         */

        CancelParam cancelParam = CancelParam.all()
                .orderId(orderId)
                .build();

        OrderCancelResponse orderCancelResponse = orderService.getOrderCancelResponse(cancelParam);

        return ResponseEntity.ok("Hello Order");
    }
}

package com.example.notification.send.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UtilTest {

    public static final int PAYMENT_READY = 0;
    public static final int PAYMENT_CONFIRM = 1;
    public static final int PARTNER_DISPATCH_REQUEST = 2;

    private static Set<Integer> AVAILABLE_CANCEL_PREV_STATUS
            = Set.of(PAYMENT_READY, PAYMENT_CONFIRM, PARTNER_DISPATCH_REQUEST);

    @Test
    public void setTest() {
        log.info("setTest");

        boolean actual = AVAILABLE_CANCEL_PREV_STATUS.contains(PAYMENT_READY);
        assertEquals(true, actual);

        boolean actual2 = AVAILABLE_CANCEL_PREV_STATUS.contains(3);
        assertEquals(true, actual2);
    }
}

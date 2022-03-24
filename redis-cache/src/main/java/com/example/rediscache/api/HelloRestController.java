package com.example.rediscache.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class HelloRestController {

    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok("Hello World" + OffsetDateTime.now());
    }
}

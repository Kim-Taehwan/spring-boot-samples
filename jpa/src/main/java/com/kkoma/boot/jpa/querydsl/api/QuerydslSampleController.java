package com.kkoma.boot.jpa.querydsl.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QuerydslSampleController {

    @GetMapping(path = "/api/querydsl")
    public ResponseEntity getSample (
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable ) {
        log.info("page: {}", pageable);

        return ResponseEntity.ok()
                .body("Hello Querydsl");
    }
}

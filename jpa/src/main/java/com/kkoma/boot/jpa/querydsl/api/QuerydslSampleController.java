package com.kkoma.boot.jpa.querydsl.api;

import com.kkoma.boot.jpa.querydsl.entity.SampleEntity;
import com.kkoma.boot.jpa.querydsl.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuerydslSampleController {
    final SampleService sampleService;

    @GetMapping(path = "/api/querydsl")
    public ResponseEntity getSample (
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "name") String name) {

        log.info("page: {}", pageable);
        Page<SampleEntity> sampleEntities = sampleService.findByName(pageable, name);

        return ResponseEntity.ok()
                .body(sampleEntities);
    }

}

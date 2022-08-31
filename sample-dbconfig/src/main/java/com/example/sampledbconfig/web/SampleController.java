package com.example.sampledbconfig.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping(path = "/samples")
    public ResponseEntity getSamples() {
        return ResponseEntity.ok("Hello Sample");
    }
}

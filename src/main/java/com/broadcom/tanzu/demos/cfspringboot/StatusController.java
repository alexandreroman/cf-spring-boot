package com.broadcom.tanzu.demos.cfspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StatusController {
    private final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @GetMapping(value = "/status/{code}", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> status(@PathVariable int code) {
        logger.atInfo().log("Generating HTTP status code: {}", code);
        return ResponseEntity.status(code).body("Status code: %s".formatted(HttpStatusCode.valueOf(code)));
    }
}

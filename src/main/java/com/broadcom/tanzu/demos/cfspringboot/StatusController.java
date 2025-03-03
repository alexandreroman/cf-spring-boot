package com.broadcom.tanzu.demos.cfspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        final var status = HttpStatus.valueOf(code);
        final var emoji = switch (status.series()) {
            case INFORMATIONAL -> "\uD83E\uDD1D";
            case SUCCESSFUL -> "\uD83D\uDE03";
            case REDIRECTION -> "\uD83D\uDC49";
            case CLIENT_ERROR -> "\uD83D\uDCA9";
            case SERVER_ERROR -> "\uD83D\uDD25";
        };
        return ResponseEntity.status(code).body("%s Status code: %s".formatted(emoji, status));
    }
}

package com.learning.moviesinfo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {
    @GetMapping("/flux")
    private Flux<Integer> show(){
        return Flux.just(1,2,3,4,5).log();
    }
    @GetMapping("/mono")
    private Mono<String> showMono(){
        return Mono.just("Hello World").log();
    }
    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> stream(){
        return Flux.just("a","b","c","d","e","f","g").delayElements(Duration.ofMillis(1000)).log();
    }
}

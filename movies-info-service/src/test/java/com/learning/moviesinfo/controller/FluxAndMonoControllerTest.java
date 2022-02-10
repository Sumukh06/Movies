package com.learning.moviesinfo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@WebFluxTest(FluxAndMonoController.class)
@AutoConfigureWebTestClient
class FluxAndMonoControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void flux(){
        webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(5);
    }
    @Test
    public void flux_Approach2(){
       var a= webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(a)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }

    @Test
    void testingMono() {
        var a=webTestClient
                .get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBody();
        StepVerifier.create(a)
                .expectNext("Hello World")
                .verifyComplete();
    }
    @Test
    void streamTest(){
      var a= webTestClient
                .get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBody();
      StepVerifier.create(a)
              .expectNext("a","b","c")
              .thenCancel()
              .verify();
    }
}
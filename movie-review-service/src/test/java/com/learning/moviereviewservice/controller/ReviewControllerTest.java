package com.learning.moviereviewservice.controller;

import com.learning.moviereviewservice.domain.Review;
import com.learning.moviereviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


import static org.mockito.Mockito.*;

@WebFluxTest(ReviewController.class)
@AutoConfigureWebTestClient
class ReviewControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReviewService service;

    @Test
    void allReviews() {
        var a= List.of(new Review("1",1L,"good movie",4.1),
                new Review("2", 2L,"great movie",4.99)  );
        when(service.allReviews()).thenReturn(Flux.fromIterable(a));
        webTestClient
                .get()
                .uri("/v1/reviews")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .hasSize(2);
        verify(service,times(1)).allReviews();

    }

    @Test
    void reviewBasedOnInfoId() {
        var a= List.of(new Review("1",1L,"good movie",4.1),
                new Review("2",1L,"great movie",4.99)  );
        when(service.findOne(anyLong())).thenReturn(Flux.fromIterable(a));
        webTestClient
                .get()
                .uri("/v1/reviews/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .hasSize(2)
                .consumeWith(listEntityExchangeResult -> {
                    var res=listEntityExchangeResult.getResponseBody();
                    assert res.get(1).equals(a.get(1));
                    assert res.get(0).equals(a.get(0));
                });
        verify(service,times(1)).findOne(anyLong());
    }

    @Test
    void addReview() {
        Review input=new Review(null,1L,"great movie",4.99);
        Review response=new Review("abc",1L,"great movie",4.99);
        when(service.add(input)).thenReturn(Mono.just(response));
        webTestClient
                .post()
                .uri("/v1/reviews")
                .bodyValue(input)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    var responseFinal=reviewEntityExchangeResult.getResponseBody();
                    assert responseFinal.equals(response);
                });
        verify(service,times(1)).add(any());
    }

    @Test
    void editReview() {
        Review input=new Review("abc",1L,"great movie",4.99);
        when(service.edit(input,"abc")).thenReturn(Mono.just(input));
        webTestClient
                .put()
                .uri("/v1/reviews/abc")
                .bodyValue(input)
                .exchange()
                .expectBody(Review.class)
                .isEqualTo(input);
        verify(service,times(1)).edit(any(),anyString());
    }

    @Test
    void deleteReview() {
        when(service.delete(anyString())).thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/reviews/a")
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class)
                .isEqualTo(null);

    }
}
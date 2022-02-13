package com.learning.moviesinfo.controller;

import com.learning.moviesinfo.domain.MovieInfo;
import com.learning.moviesinfo.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(MovieInfoController.class)
@AutoConfigureWebTestClient
class MovieInfoControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    MovieInfoService service;

    final String url="/v1/movieinfos";

    @Test
    public void getAllMovies(){
        var movieinfos = List.of(new MovieInfo(null, "Love Mocktail",
                        2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Ajay", "Sushma"), LocalDate.parse("2018-07-18")));
        when(service.getAll()).thenReturn(Flux.fromIterable(movieinfos));
        webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }
    @Test
    public void getAllMoviesByYear(){
        var movieinfos = List.of(new MovieInfo(null, "Love Mocktail",
                        2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Ajay", "Sushma"), LocalDate.parse("2018-07-18")));
        when(service.findByYear(isA(Integer.class))).thenReturn(Flux.just(movieinfos.get(0)));
        webTestClient
                .get()
                .uri(url+"/?year=2005")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(1);
    }
    @Test
    public void getOneMovieInfo(){
        MovieInfo a=new MovieInfo(null, "Love Mocktail",
                2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));
        when(service.findOne("1")).thenReturn(Mono.just(a));

        var b=webTestClient
                .get()
                .uri(url+"/1")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(MovieInfo.class)
                .getResponseBody();
        StepVerifier.create(b)
                .expectNext(a)
                .verifyComplete();
    }
    @Test
    public void getOneMovieInfoEmpty(){

        when(service.findOne(isA(String.class))).thenReturn(Mono.empty());

        var b=webTestClient
                .get()
                .uri(url+"/1")
                .exchange()
                .expectStatus()
                .isEqualTo(404);
    }
    @Test
    public void addMovieInfo(){
        MovieInfo a=new MovieInfo("A123s", "Love Mocktail",
                2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));
        when(service.addMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(a));

        var b= webTestClient
                .post()
                .uri(url)
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(MovieInfo.class)
                .getResponseBody();
        StepVerifier.create(b)
                .expectNext(a)
                .verifyComplete();

    }
    @Test
    public void updateMovieInfo(){
        String id="A123s";
        MovieInfo a=new MovieInfo(null, "Love Mocktail",
                2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));
        MovieInfo newB=new MovieInfo("A123s", "Love Mocktail",
                2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));
        when(service.updateMovieInfo(isA(MovieInfo.class), isA(String.class)))
                .thenReturn(Mono.just(newB));

        var b= webTestClient
                .put()
                .uri(url+"/{id}","A123s")
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(MovieInfo.class)
                .getResponseBody();
        StepVerifier.create(b)
                .expectNext(newB)
                .verifyComplete();
    }
    @Test
    public void updateMovieInfoNotFound(){
        String id="A123s";
        MovieInfo a=new MovieInfo(null, "Love Mocktail",
                2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));

        when(service.updateMovieInfo(isA(MovieInfo.class), isA(String.class)))
                .thenReturn(Mono.empty());

        var b= webTestClient
                .put()
                .uri(url+"/{id}","A123s")
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isEqualTo(404);
    }
    @Test
    public void deleteMovieInfo(){

        when(service.deleteMovieInfo(isA(String.class))).thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri(url+"/{id}","hello")
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }
    @Test
    public void addMovieInfoValidation(){
        MovieInfo a=new MovieInfo("A123s", "",
                -2005, List.of("Raj", "Shetty"), LocalDate.parse("2015-06-15"));
        when(service.addMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(a));

        var b= webTestClient
                .post()
                .uri(url)
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .returnResult();
        System.out.println(b);

    }
    @Test
    public void addMovieInfoCastListValidation(){
        MovieInfo a=new MovieInfo("A123s", "Love Mocktail",
                2005, List.of("apple", ""), LocalDate.parse("2015-06-15"));
        when(service.addMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(a));

        var b= webTestClient
                .post()
                .uri(url)
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .returnResult();
        System.out.println(b);

    }

}
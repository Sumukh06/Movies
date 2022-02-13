package com.learning.moviesinfo.controller;


import com.learning.moviesinfo.domain.MovieInfo;
import com.learning.moviesinfo.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/movieinfos")
public class MovieInfoController {

    @Autowired
    MovieInfoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo){
        return service.addMovieInfo(movieInfo);
    }

    @GetMapping
    private Flux<MovieInfo> getAllMovies(@RequestParam(value = "year",required = false) Integer year){
       if(year!=null){
           return service.findByYear(year);
       }
       else {
        return service.getAll();
        }
    }
    @GetMapping("/{id}")
    private Mono<ResponseEntity<MovieInfo>> findOne(@PathVariable String id){
        return service.findOne(id)
                .map(movieInfo -> {
                    return ResponseEntity.status(200).body(movieInfo);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }
    @PutMapping("/{id}")
    private Mono<ResponseEntity<MovieInfo>> updateOne(@PathVariable String id, @RequestBody MovieInfo movieInfo) {
        return service.updateMovieInfo(movieInfo, id)
                .flatMap(movieInfo1 ->{
                    return Mono.just(ResponseEntity.status(200).body(movieInfo1));
                } )
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private Mono<Void> delete(@PathVariable String id){
        return service.deleteMovieInfo(id);
    }
}

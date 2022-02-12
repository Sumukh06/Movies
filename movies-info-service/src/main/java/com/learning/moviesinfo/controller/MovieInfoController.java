package com.learning.moviesinfo.controller;


import com.learning.moviesinfo.domain.MovieInfo;
import com.learning.moviesinfo.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/v1/movieinfos")
public class MovieInfoController {

    @Autowired
    MovieInfoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
        return service.addMovieInfo(movieInfo);
    }

    @GetMapping
    private Flux<MovieInfo> getAllMovies(){
        return service.getAll();
    }
    @GetMapping("/{id}")
    private Mono<MovieInfo> findOne(@PathVariable String id){
        return service.findOne(id);
    }
    @PutMapping("/{id}")
    private Mono<MovieInfo> updateOne(@PathVariable String id,@RequestBody MovieInfo movieInfo) {
        return service.updateMovieInfo(movieInfo, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private Mono<Void> delete(@PathVariable String id){
        return service.deleteMovieInfo(id);
    }
}

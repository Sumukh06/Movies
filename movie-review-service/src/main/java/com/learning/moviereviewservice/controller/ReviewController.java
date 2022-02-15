package com.learning.moviereviewservice.controller;

import com.learning.moviereviewservice.domain.Review;
import com.learning.moviereviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {
    @Autowired
    ReviewService service;
    @GetMapping
    public Flux<Review> allReviews(){
        return  service.allReviews();
    }
    @GetMapping("/{id}")
    public Flux<ResponseEntity<Review>> reviewBasedOnInfoId(@PathVariable Long id){
        return service.findOne(id)
                .map(review -> {
                    return ResponseEntity.status(200).body(review);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Review> addReview(@RequestBody Review review){
        return service.add(review);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Review>> editReview(@RequestBody Review review,@PathVariable String id){
        return service.edit(review, id)
                .map(review1 -> {
                    return ResponseEntity.status(HttpStatus.CREATED).body(review1);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    public Mono<Void> deleteReview(@PathVariable String id){
        return service.delete(id);
    }

}

package com.learning.moviereviewservice.service;

import com.learning.moviereviewservice.domain.Review;
import com.learning.moviereviewservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;


    public Flux<Review> allReviews(){
        return repository.findAll();
    }

    public Flux<Review> findOne(Long id){
        return repository.findByMovieInfoId(id);
    }

    public Mono<Review> add(Review review){
        return repository.save(review);
    }
    public Mono<Review> edit(Review review,String id){
        return repository.findById(id)
                .flatMap(review1 -> {
                    review1.setMovieInfoId(review.getMovieInfoId());
                    review1.setComment(review.getComment());
                    review1.setRating(review.getRating());
                    return repository.save(review1);
                });
    }
    public Mono<Void> delete(String id){
        return repository.deleteById(id);
    }
}

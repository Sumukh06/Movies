package com.learning.moviesinfo.repository;

import com.learning.moviesinfo.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo,String> {
}

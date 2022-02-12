package com.learning.moviesinfo.service;

import com.learning.moviesinfo.domain.MovieInfo;
import com.learning.moviesinfo.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {
    @Autowired
    MovieInfoRepository repository;

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo){
        return repository.save(movieInfo);
    }

    public Flux<MovieInfo> getAll(){
        return repository.findAll();
    }
    public Mono<MovieInfo> findOne(String id){
        return repository.findById(id);
    }
    public Mono<MovieInfo> updateMovieInfo(MovieInfo movieInfoUpdate,String id){
        return repository.findById(id)
                .flatMap(movieInfo -> {
                    movieInfo.setName(movieInfoUpdate.getName());
                    movieInfo.setCast(movieInfoUpdate.getCast());
                    movieInfo.setYear(movieInfoUpdate.getYear());
                    movieInfo.setRelease_date(movieInfoUpdate.getRelease_date());
                    return repository.save(movieInfo);
                });
    }
    public Mono<Void> deleteMovieInfo(String id){
        return repository.deleteById(id);
    }
}

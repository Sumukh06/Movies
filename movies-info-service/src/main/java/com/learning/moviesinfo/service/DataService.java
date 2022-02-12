package com.learning.moviesinfo.service;

import com.learning.moviesinfo.domain.MovieInfo;
import com.learning.moviesinfo.repository.MovieInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class DataService implements CommandLineRunner {
    @Autowired
    MovieInfoRepository repository;
    @Override
    public void run(String... args) throws Exception {
        log.info("data service running.... adding dummy data to db");
        var movieinfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
        repository.saveAll(movieinfos).blockLast();
    }
}

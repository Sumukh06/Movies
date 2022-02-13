package com.learning.moviesinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class MovieInfo {
    @Id
    private String movieInfoId;
    @NotBlank(message = "Movie info name cannot be blank")
    private String name;
    @NotNull
    @Positive(message = "movie info year field must be a positive value")
    private Integer year;
    private List<@NotBlank(message = "movie info cast should not be blank") String> cast;
    private LocalDate release_date;
}

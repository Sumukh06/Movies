package com.learning.moviesservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfo {
    private String movieInfoId;
    @NotBlank(message = "Movie info name cannot be blank")
    private String name;
    @NotNull
    @Positive(message = "movie info year field must be a positive value")
    private Integer year;
    private List<@NotBlank(message = "movie info cast should not be blank") String> cast;
    private LocalDate release_date;
}

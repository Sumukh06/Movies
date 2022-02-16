package com.learning.moviesservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String reviewId;
    @NotNull(message = "rating.movieInfoId cannot be null")
    private Long movieInfoId;
    private String comment;
    @Min(value = 0L,message = "rating cannot be negative")
    private Double rating;
}

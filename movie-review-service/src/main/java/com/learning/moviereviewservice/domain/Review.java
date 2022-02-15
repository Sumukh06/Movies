package com.learning.moviereviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Review {
    @Id
    private String reviewId;
    @NotNull(message = "rating.movieInfoId cannot be null")
    private Long movieInfoId;
    private String comment;
    @Min(value = 0L,message = "rating cannot be negative")
    private Double rating;
}

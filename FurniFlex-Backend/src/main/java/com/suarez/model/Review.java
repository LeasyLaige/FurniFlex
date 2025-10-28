package com.suarez.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Integer id;
    private Integer productId;
    private String author;
    private Integer rating;
    private String comment;
    private LocalDateTime created;
}

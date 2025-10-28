package com.suarez.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterSubscription {
    private Integer id;
    private String email;
    private LocalDateTime created;
}

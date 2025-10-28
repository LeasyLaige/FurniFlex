package com.suarez.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "newsletter_subscription")
public class NewsletterSubscriptionData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsletter_subscription_seq")
    @SequenceGenerator(name = "newsletter_subscription_seq", sequenceName = "newsletter_subscription_SEQ", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDateTime created;
}

package com.suarez.repository;

import com.suarez.entity.NewsletterSubscriptionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterSubscriptionDataRepository extends JpaRepository<NewsletterSubscriptionData, Integer> {
    Optional<NewsletterSubscriptionData> findByEmail(String email);
}

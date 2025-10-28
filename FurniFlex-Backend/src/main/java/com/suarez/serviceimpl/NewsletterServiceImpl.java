package com.suarez.serviceimpl;

import com.suarez.entity.NewsletterSubscriptionData;
import com.suarez.model.NewsletterSubscription;
import com.suarez.repository.NewsletterSubscriptionDataRepository;
import com.suarez.service.NewsletterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterSubscriptionDataRepository repo;

    public NewsletterServiceImpl(NewsletterSubscriptionDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public NewsletterSubscription subscribe(String email) {
        var existing = repo.findByEmail(email).orElse(null);
        if (existing != null) {
            return toModel(existing);
        }
        NewsletterSubscriptionData nd = new NewsletterSubscriptionData();
        nd.setEmail(email);
        nd.setCreated(LocalDateTime.now());
        NewsletterSubscriptionData saved = repo.save(nd);
        return toModel(saved);
    }

    private NewsletterSubscription toModel(NewsletterSubscriptionData nd) {
        return new NewsletterSubscription(nd.getId(), nd.getEmail(), nd.getCreated());
    }
}

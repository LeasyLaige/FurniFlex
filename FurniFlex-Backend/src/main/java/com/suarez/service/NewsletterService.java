package com.suarez.service;

import com.suarez.model.NewsletterSubscription;

public interface NewsletterService {
    NewsletterSubscription subscribe(String email);
}

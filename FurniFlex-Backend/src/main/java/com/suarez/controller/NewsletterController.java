package com.suarez.controller;

import com.suarez.model.NewsletterSubscription;
import com.suarez.service.NewsletterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin
public class NewsletterController {

    private final NewsletterService service;

    public NewsletterController(NewsletterService service) {
        this.service = service;
    }

    public record SubscribeRequest(String email) {}

    @PostMapping("/subscribe")
    public ResponseEntity<NewsletterSubscription> subscribe(@RequestBody SubscribeRequest req) {
        return ResponseEntity.ok(service.subscribe(req.email()));
    }
}

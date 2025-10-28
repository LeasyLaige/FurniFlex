package com.suarez.controller;

import com.suarez.model.Review;
import com.suarez.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> byProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.listByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return ResponseEntity.ok(service.create(review));
    }
}

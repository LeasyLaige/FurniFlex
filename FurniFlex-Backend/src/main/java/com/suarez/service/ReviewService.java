package com.suarez.service;

import com.suarez.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> listByProductId(Integer productId);
    Review create(Review review);
}

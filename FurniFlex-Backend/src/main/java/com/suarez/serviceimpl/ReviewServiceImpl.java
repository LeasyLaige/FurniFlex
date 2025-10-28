package com.suarez.serviceimpl;

import com.suarez.entity.ProductData;
import com.suarez.entity.ReviewData;
import com.suarez.model.Review;
import com.suarez.repository.ProductDataRepository;
import com.suarez.repository.ReviewDataRepository;
import com.suarez.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDataRepository repo;
    private final ProductDataRepository products;

    public ReviewServiceImpl(ReviewDataRepository repo, ProductDataRepository products) {
        this.repo = repo;
        this.products = products;
    }

    @Override
    public List<Review> listByProductId(Integer productId) {
        List<ReviewData> data = repo.findByProduct_Id(productId);
        List<Review> result = new ArrayList<>();
        for (ReviewData rd : data) {
            result.add(toModel(rd));
        }
        return result;
    }

    @Override
    public Review create(Review review) {
        ProductData pd = products.findById(review.getProductId()).orElseThrow();
        ReviewData rd = new ReviewData();
        rd.setProduct(pd);
        rd.setAuthor(review.getAuthor());
        rd.setRating(review.getRating());
        rd.setComment(review.getComment());
        rd.setCreated(LocalDateTime.now());
        ReviewData saved = repo.save(rd);
        return toModel(saved);
    }

    private Review toModel(ReviewData rd) {
        Review r = new Review();
        r.setId(rd.getId());
        r.setProductId(rd.getProduct() != null ? rd.getProduct().getId() : null);
        r.setAuthor(rd.getAuthor());
        r.setRating(rd.getRating());
        r.setComment(rd.getComment());
        r.setCreated(rd.getCreated());
        return r;
    }
}

package org.sliitprojectspring.review.controller;

import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.review.dto.ReviewDto;
import org.sliitprojectspring.review.dto.ReviewRequest;
import org.sliitprojectspring.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewDto addReview(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }

    @GetMapping("/property/{propertyId}")
    public List<ReviewDto> getReviewsByProperty(@PathVariable Long propertyId) {
        return reviewService.getReviewsByProperty(propertyId);
    }

    @GetMapping("/property/{propertyId}/average")
    public double getAverageRating(@PathVariable Long propertyId) {
        return reviewService.getAverageRating(propertyId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}

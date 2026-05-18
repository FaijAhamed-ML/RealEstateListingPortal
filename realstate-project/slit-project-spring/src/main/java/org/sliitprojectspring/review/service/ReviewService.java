package org.sliitprojectspring.review.service;

import org.springframework.stereotype.Service;
import org.sliitprojectspring.property.model.Property;
import org.sliitprojectspring.property.repository.PropertyRepository;
import org.sliitprojectspring.review.dto.ReviewDto;
import org.sliitprojectspring.review.dto.ReviewRequest;
import org.sliitprojectspring.review.model.Review;
import org.sliitprojectspring.review.repository.ReviewRepository;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public ReviewDto addReview(ReviewRequest request) {
        Property property = propertyRepository.findById(request.propertyId())
            .orElseThrow(() -> new RuntimeException("Property not found"));
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setComment(request.comment());
        review.setRating(request.rating());
        review.setProperty(property);
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);
        return mapToDto(saved);
    }

    public List<ReviewDto> getReviewsByProperty(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public double getAverageRating(Long propertyId) {
        List<Review> reviews = reviewRepository.findByPropertyId(propertyId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    private ReviewDto mapToDto(Review review) {
        return new ReviewDto(
            review.getId(),
            review.getComment(),
            review.getRating(),
            review.getProperty().getId(),
            review.getUser().getId(),
            review.getUser().getName(),
            review.getCreatedAt()
        );
    }
}

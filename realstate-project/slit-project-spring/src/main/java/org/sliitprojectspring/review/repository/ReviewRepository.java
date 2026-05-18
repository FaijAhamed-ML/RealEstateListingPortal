package org.sliitprojectspring.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sliitprojectspring.review.model.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPropertyId(Long propertyId);
    List<Review> findByUserId(Long userId);
}

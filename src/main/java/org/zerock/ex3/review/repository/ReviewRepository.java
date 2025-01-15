package org.zerock.ex3.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex3.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

}

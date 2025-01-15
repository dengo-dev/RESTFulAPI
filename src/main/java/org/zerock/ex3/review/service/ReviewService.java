package org.zerock.ex3.review.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex3.review.dto.ReviewDTO;
import org.zerock.ex3.review.dto.ReviewPageRequestDTO;
import org.zerock.ex3.review.entity.ReviewEntity;
import org.zerock.ex3.review.exception.ReviewExceptions;
import org.zerock.ex3.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReviewService {

  private final ReviewRepository reviewRepository;


  public ReviewDTO register(ReviewDTO reviewDTO) {
    log.info("review register..........");
    try {
      ReviewEntity reviewEntity = reviewDTO.toEntity();

      reviewRepository.save(reviewEntity);

      return new ReviewDTO(reviewEntity);
    } catch (DataIntegrityViolationException e) {
      throw ReviewExceptions.REVIEW_PRODUCT_NOT_FOUND.get();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw ReviewExceptions.REVIEW_NOT_REGISTERED.get();
    }

  }
  public ReviewDTO read(Long rno) {
    ReviewEntity reviewEntity = reviewRepository.findById(rno).orElseThrow(ReviewExceptions.REVIEW_NOT_FOUND::get);

    return new ReviewDTO(reviewEntity);
  }

  public void remove(Long rno) {
    ReviewEntity reviewEntity = reviewRepository.findById(rno).orElseThrow(ReviewExceptions.REVIEW_NOT_FOUND::get);

    try {
      reviewRepository.delete(reviewEntity);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw ReviewExceptions.REVIEW_NOT_REMOVED.get();
    }

  }

  public ReviewDTO modify(ReviewDTO reviewDTO) {
    ReviewEntity reviewEntity = reviewRepository.findById(reviewDTO).orElseThrow(ReviewExceptions.REVIEW_NOT_FOUND::get);

    try {
      reviewEntity.changeReviewText(reviewDTO.getReviewText());
      reviewEntity.changeScore(reviewDTO.getScore());
      return new ReviewDTO(reviewEntity);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw ReviewExceptions.REVEIW_NOT_MODIFIED.get();
    }
  }

  public Page<ReviewDTO> getList(ReviewPageRequestDTO reviewPageRequestDTO) {
    Long pno = reviewPageRequestDTO.getPno();
    Pageable pageable = reviewPageRequestDTO.getPageable(Sort.by("rno").descending());

    return reviewRepository.getListByPno(pno, pageable);
  }
}


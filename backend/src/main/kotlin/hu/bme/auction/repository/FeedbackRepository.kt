package hu.bme.auction.repository;

import hu.bme.auction.entity.Feedback
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedbackRepository : JpaRepository<Feedback, Long> {
}
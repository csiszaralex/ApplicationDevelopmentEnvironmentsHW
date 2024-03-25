package hu.bme.auction.repository;

import hu.bme.auction.entity.Bid
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BidRepository : JpaRepository<Bid, Long> {
}
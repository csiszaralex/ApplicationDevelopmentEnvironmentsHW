package hu.bme.auction.repository;

import hu.bme.auction.entity.Watchlist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WatchlistRepository : JpaRepository<Watchlist, Long> {
}
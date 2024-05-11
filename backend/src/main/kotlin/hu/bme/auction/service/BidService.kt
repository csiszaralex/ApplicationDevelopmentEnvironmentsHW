package hu.bme.auction.service

import hu.bme.auction.dao.BidRepository
import hu.bme.auction.entity.Bid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BidService(val bidRepository: BidRepository) {
    fun getAll(): List<Bid> {
        return bidRepository.findAll()
    }

    fun getOne(id: Long): Bid? {
        return bidRepository.findByIdOrNull(id)
    }

    fun create(bid: Bid): Bid {
        return bidRepository.save(bid)
    }

    fun update(id: Long, bid: Bid): Bid? {
        val newBid = bidRepository.findByIdOrNull(id) ?: return null
        bid.amount?.let { newBid.amount = it }
        bid.user?.let { newBid.user = it }
        bid.item?.let { newBid.item = it }
        return bidRepository.save(newBid)
    }

    fun delete(id: Long) {
        return bidRepository.deleteById(id)
    }
}
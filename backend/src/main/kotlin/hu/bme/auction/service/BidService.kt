package hu.bme.auction.service

import hu.bme.auction.dao.BidRepository
import hu.bme.auction.dao.ItemRepository
import hu.bme.auction.entity.Bid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BidService(val bidRepository: BidRepository, val itemRepository: ItemRepository, val emailSenderService: EmailSenderService) {
    fun getAll(): List<Bid> {
        return bidRepository.findAll()
    }

    fun getOne(id: Long): Bid? {
        return bidRepository.findByIdOrNull(id)
    }

    fun create(bid: Bid): Bid {
        val item = itemRepository.findWithBidsAndWatchlistsById(bid.item!!.id!!) ?: throw IllegalArgumentException("Item not found")
        var maxEddig = 0
        item.bids.forEach {
            if (it.amount!! > maxEddig) {
                maxEddig = it.amount!!
            }
        }
        if (bid.amount!! <= maxEddig) throw IllegalArgumentException("Bid amount must be greater than the current highest bid")

        item.watchlists.forEach {
            emailSenderService.sendEmailForNewBid(item.title!!, bid.amount!!, it.user!!.email!!)
        }

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
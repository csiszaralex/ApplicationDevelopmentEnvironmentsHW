package hu.bme.auction.service

import hu.bme.auction.dao.BidRepository
import hu.bme.auction.dao.ItemRepository
import hu.bme.auction.dao.UserRepository
import hu.bme.auction.dto.CreateBidDto
import hu.bme.auction.entity.Bid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BidService(
    val bidRepository: BidRepository,
    val itemRepository: ItemRepository,
    val emailSenderService: EmailSenderService,
    private val userRepository: UserRepository
) {
    fun getAll(): List<Bid> {
        val bids = bidRepository.findAll()
        bids.forEach {
            it.item?.bids = mutableSetOf()
            it.user?.bids = mutableSetOf()
            it.user?.items = mutableSetOf()
            it.item?.user = null
            it.item?.category?.items = mutableSetOf()
        }
        return bids
    }

    fun getOne(id: Long): Bid? {
        return bidRepository.findByIdOrNull(id)
    }

    fun create(bid: CreateBidDto): Bid {
        val item = itemRepository.findByIdOrNull(bid.itemId) ?: throw IllegalArgumentException("Item not found")
        if(item.startingBid >= bid.amount) throw IllegalArgumentException("Bid amount must be greater than the starting bid")
        item.bids.forEach { if (it.amount!! >= bid.amount) throw IllegalArgumentException("Bid amount must be greater than the current highest bid") }
        val user = userRepository.findByIdOrNull(bid.userId) ?: throw IllegalArgumentException("User not found")

        val newBid = Bid()
        newBid.amount = bid.amount
        newBid.item = item
        newBid.user = user

        return bidRepository.save(newBid)

//        item.watchlists.forEach {
//            emailSenderService.sendEmailForNewBid(item.title!!, bid.amount!!, it.user!!.email!!)
//        }
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
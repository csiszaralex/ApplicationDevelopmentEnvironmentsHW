package hu.bme.auction.service

import hu.bme.auction.dao.ItemRepository
import hu.bme.auction.dao.UserRepository
import hu.bme.auction.dao.WatchlistRepository
import hu.bme.auction.entity.Item
import hu.bme.auction.entity.Watchlist
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ItemService(
    val itemRepository: ItemRepository,
    private val userRepository: UserRepository,
    private val watchlistRepository: WatchlistRepository
) {

    fun getAll(): List<Item> {
        return itemRepository.findAll()
    }

    fun getOne(id: Long): Item? {
        return itemRepository.findByIdOrNull(id)
    }

    fun create(Item: Item): Item {
        return itemRepository.save(Item)
    }

    fun update(id: Long, item: Item): Item? {
        val newItem = itemRepository.findByIdOrNull(id) ?: return null
        item.id?.let { newItem.id = it }
        item.title?.let { newItem.title = it }
        item.quality?.let { newItem.quality = it }
        item.payed?.let { newItem.payed = it }
        item.startingBid?.let { newItem.startingBid = it }
        item.user?.let { newItem.user = it }
        item.bids?.let { newItem.bids = it }
        item.category?.let { newItem.category = it }
        item.watchlists?.let { newItem.watchlists = it }
        return itemRepository.save(newItem)
    }

    fun delete(id: Long) {
        return itemRepository.deleteById(id)
    }

    fun subscribeWatchlist(itemId: Long, userId: Long) {
        val item = itemRepository.findByIdOrNull(itemId) ?: return
        val user = userRepository.findByIdOrNull(userId) ?: return
        val watchlist = item.watchlists.find { it.user?.id == userId }
        if (watchlist == null) {
            item.watchlists.add(Watchlist(user = user, item = item))
            itemRepository.save(item)
        }
    }

    fun unsubscribeWatchlist(itemId: Long, userId: Long) {
        val item = itemRepository.findByIdOrNull(itemId) ?: return
        val watchlist = item.watchlists.find { it.user?.id == userId }
        if (watchlist != null) {
            item.watchlists.remove(watchlist)
            itemRepository.save(item)
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun removeEveryWatchFromPayedItemDaily() {
        val items = itemRepository.findAll()
        items.forEach { item ->
            if (item.payed) {
                item.watchlists.forEach(watchlistRepository::delete)
                item.watchlists.clear()
                itemRepository.save(item)
            }
        }
    }

}
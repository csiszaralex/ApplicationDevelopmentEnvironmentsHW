package hu.bme.auction.service

import hu.bme.auction.dao.CategoryRepository
import hu.bme.auction.dao.ItemRepository
import hu.bme.auction.dao.UserRepository
import hu.bme.auction.dao.WatchlistRepository
import hu.bme.auction.dto.CreateItemDto
import hu.bme.auction.entity.Item
import hu.bme.auction.entity.Watchlist
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ItemService(
    val itemRepository: ItemRepository,
    private val userRepository: UserRepository,
    private val watchlistRepository: WatchlistRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryService: CategoryService
) {

    fun getAll(): List<Item> {
        return itemRepository.findAll()
    }

    fun getOne(id: Long): Item? {
        return itemRepository.findByIdOrNull(id)
    }

    fun create(item: CreateItemDto): Item {
        val i = Item()
        i.title = item.title
        i.payed = item.payed
        i.startingBid = item.startingBid
        i.user = userRepository.findByIdOrNull(item.userId)
        i.category = categoryService.getByNameOrCreate(item.categoryName)
        val newI = itemRepository.save(i)
        newI.category?.items = mutableSetOf()
        return newI
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

        itemRepository.findByIdOrNull(id) ?: return
        return itemRepository.deleteById(id)
    }

    fun subscribeWatchlist(itemId: Long, userId: Long): Int {
        val item = itemRepository.findByIdOrNull(itemId) ?: return 0
        val user = userRepository.findByIdOrNull(userId) ?: return 0
        val watchlist = item.watchlists.find { it.user?.id == userId }
        if (watchlist == null) {
            val watch = Watchlist()
            watch.item = item
            watch.user = user
            watchlistRepository.save(watch)
            return 1
        }
        watchlistRepository.delete(watchlist)
        return 2

    }

    @Scheduled(cron = "0 0 0 * * *")
    fun removeEveryWatchFromPayedItem() {
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
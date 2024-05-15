package hu.bme.auction.controller

import hu.bme.auction.dto.CreateItemDto
import hu.bme.auction.dto.ItemWithDetailsDto
import hu.bme.auction.entity.Item
import hu.bme.auction.service.ItemService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemController(val itemService: ItemService) {
    private val log = LoggerFactory.getLogger(javaClass)
    @GetMapping()
    fun getAll(): ResponseEntity<List<ItemWithDetailsDto>> {
        val items: List<Item> = itemService.getAll()
        if (items.isEmpty()) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        val finalItems: List<ItemWithDetailsDto> = items.map {
            val itemWithDetailsDto = ItemWithDetailsDto()
            itemWithDetailsDto.id = it.id ?: 0
            itemWithDetailsDto.name = it.title ?: "No name"
            itemWithDetailsDto.ownerId = it.user?.id ?: 0
            itemWithDetailsDto.ownerName = it.user?.name ?: "No owner"
            itemWithDetailsDto.highestBid = it.bids.maxByOrNull { i -> i.amount?:0 }?.amount ?: it.startingBid
            itemWithDetailsDto.highestBidderName = it.bids.maxByOrNull { i-> i.amount?:0 }?.user?.name ?: it.user?.name ?: "No owner"
            itemWithDetailsDto.category = it.category?.name ?: "No category"
            itemWithDetailsDto
        }
        return ResponseEntity.ok(finalItems)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<Item> {
        val cat: Item = itemService.getOne(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(cat)
    }

    @PostMapping()
    fun create(@RequestBody i: CreateItemDto): Item {
        val cI = itemService.create(i)
        log.info("Item created: $cI")
        cI.user = null
        return cI
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody item: Item): ResponseEntity<Item> {
        val newItem = itemService.update(id, item) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        log.info("Item updated: $newItem")
        return ResponseEntity.ok(newItem)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        log.info("Item deleted: $id")
        return itemService.delete(id)
    }

    @GetMapping("/{itemId}/subscribe/{userId}")
    fun subscribeWatchlist(@PathVariable itemId: Long, @PathVariable userId: Long) {
        itemService.subscribeWatchlist(itemId, userId)
        log.info("User $userId subscribed to item $itemId")
    }

    @GetMapping("/{itemId}/unsubscribe/{userId}")
    fun unsubscribeWatchlist(@PathVariable itemId: Long, @PathVariable userId: Long) {
        itemService.unsubscribeWatchlist(itemId, userId)
        log.info("User $userId unsubscribed from item $itemId")
    }
}
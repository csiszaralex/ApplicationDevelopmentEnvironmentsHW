package hu.bme.auction.controller

import hu.bme.auction.entity.Item
import hu.bme.auction.service.ItemService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemController(val itemService: ItemService) {
    @GetMapping()
    fun getAll(): ResponseEntity<List<Item>> {
        val lista: List<Item> = itemService.getAll()
        if (lista.isEmpty()) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<Item> {
        val cat: Item = itemService.getOne(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(cat)
    }

    @PostMapping()
    fun create(@RequestBody cat: Item): Item {
        return itemService.create(cat)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody item: Item): ResponseEntity<Item> {
        val newItem = itemService.update(id, item) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(newItem)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        return itemService.delete(id)
    }

    @GetMapping("/{itemId}/subscribe/{userId}")
    fun subscribeWatchlist(@PathVariable itemId: Long, @PathVariable userId: Long) {
        itemService.subscribeWatchlist(itemId, userId)
    }

    @GetMapping("/{itemId}/unsubscribe/{userId}")
    fun unsubscribeWatchlist(@PathVariable itemId: Long, @PathVariable userId: Long) {
        itemService.unsubscribeWatchlist(itemId, userId)
    }
}
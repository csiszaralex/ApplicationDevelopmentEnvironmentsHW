package hu.bme.auction.controller

import hu.bme.auction.entity.Bid
import hu.bme.auction.service.BidService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bid")
class BidController(val bidService: BidService) {
    @GetMapping()
    fun getAll(): ResponseEntity<List<Bid>> {
        val lista: List<Bid> = bidService.getAll()
        if (lista.isEmpty()) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<Bid> {
        val bid: Bid = bidService.getOne(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(bid)
    }

    @PostMapping()
    fun create(@RequestBody bid: Bid): Bid {
        return bidService.create(bid)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody bid: Bid): ResponseEntity<Bid> {
        val newBid = bidService.update(id, bid) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(newBid)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        return bidService.delete(id)
    }
}
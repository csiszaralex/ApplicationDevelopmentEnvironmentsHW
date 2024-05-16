package hu.bme.auction.controller

import hu.bme.auction.dto.CreateBidDto
import hu.bme.auction.entity.Bid
import hu.bme.auction.service.BidService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bid")
class BidController(val bidService: BidService) {
    private val log = LoggerFactory.getLogger(javaClass)
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
        bid.user?.items = mutableSetOf()
        return ResponseEntity.ok(bid)
    }

    @PostMapping()
    fun create(@RequestBody bid: CreateBidDto): Bid {
        val newBid = bidService.create(bid)
        newBid.user?.items = mutableSetOf()
        newBid.user?.bids = mutableSetOf()
        newBid.item = null
        log.info("Bid created: $newBid")
        return newBid
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody bid: Bid): ResponseEntity<Bid> {
        val newBid = bidService.update(id, bid) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        log.info("Bid updated: $newBid")
        return ResponseEntity.ok(newBid)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        log.info("Bid deleted: $id")
        return bidService.delete(id)
    }
}
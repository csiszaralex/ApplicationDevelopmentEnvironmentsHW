package hu.bme.auction.controller

import hu.bme.auction.dto.CreateBidDto
import hu.bme.auction.entity.Bid
import hu.bme.auction.service.BidService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SpringBootTest
class BidControllerTest() {

    /*
        * Test creating a bid
        * @return ResponseEntity with status code 201
     */
    @Test
    fun testCreateBid() {
        val bid = Bid()
        val bidService = BidService()
        val bidController = BidController(bidService)
        val createBidDto = CreateBidDto()
        val responseEntity = bidController.create(createBidDto)
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
    }

    /*
        * Test getting all bids
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetBids() {
        val bidService = BidService()
        val bidController = BidController(bidService)
        val responseEntity = bidController.getBids()
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }
    
    /*
        * Test getting a bid
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetBid() {
        val bidService = BidService()
        val bidController = BidController(bidService)
        val responseEntity = bidController.getBid(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Test deleting a bid
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testDeleteBid() {
        val bidService = BidService()
        val bidController = BidController(bidService)
        val responseEntity = bidController.deleteBid(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Test updating a bid
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testUpdateBid() {
        val bidService = BidService()
        val bidController = BidController(bidService)
        val createBidDto = CreateBidDto()
        val responseEntity = bidController.updateBid(1, createBidDto)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }
}
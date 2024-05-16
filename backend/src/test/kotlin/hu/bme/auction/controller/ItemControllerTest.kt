package hu.bme.auction.controller

import hu.bme.auction.dto.CreateItemDto
import hu.bme.auction.dto.ItemWithDetailsDto
import hu.bme.auction.entity.Item
import hu.bme.auction.service.ItemService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SpringBootTest
class ItemController() {
    /*
        * Create an item with the given item
        * @param item to create
        * @return ResponseEntity with status code 201
     */
    @Test
    fun testCreateItem() {
        val item = Item()
        val itemService = ItemService()
        val itemController = ItemController(itemService)
        val createItemDto = CreateItemDto()
        val responseEntity = itemController.createItem(createItemDto)
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
    }

    /*
        * Get all items
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetItems() {
        val itemService = ItemService()
        val itemController = ItemController(itemService)
        val responseEntity = itemController.getItems()
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Get an item
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetItem() {
        val itemService = ItemService()
        val itemController = ItemController(itemService)
        val responseEntity = itemController.getItem(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Delete an item
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testDeleteItem() {
        val itemService = ItemService()
        val itemController = ItemController(itemService)
        val responseEntity = itemController.delete(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Update an item
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testUpdateItem() {
        val itemService = ItemService()
        val itemController = ItemController(itemService)
        val createItemDto = CreateItemDto()
        val responseEntity = itemController.updateItem(1, createItemDto)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }
}
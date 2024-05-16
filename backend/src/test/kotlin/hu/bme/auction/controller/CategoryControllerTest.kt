package hu.bme.auction.controller

import hu.bme.auction.entity.Category
import hu.bme.auction.service.CategoryService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SpringBootTest
class CategoryController(val categoryService: CategoryService) {
    
}
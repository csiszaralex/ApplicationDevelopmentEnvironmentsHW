package hu.bme.auction.controller

import hu.bme.auction.entity.Category
import hu.bme.auction.service.CategoryService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SpringBootTest
class CategoryController(val categoryService: CategoryService) {
    @Test
    fun testCreateCategory() {
        val category = Category()
        val categoryService = CategoryService()
        val categoryController = CategoryController(categoryService)
        val responseEntity = categoryController.createCategory(category)
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
    }

    @Test
    fun testGetCategories() {
        val categoryService = CategoryService()
        val categoryController = CategoryController(categoryService)
        val responseEntity = categoryController.getCategories()
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    fun testGetCategory() {
        val categoryService = CategoryService()
        val categoryController = CategoryController(categoryService)
        val responseEntity = categoryController.getCategory(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    fun testDeleteCategory() {
        val categoryService = CategoryService()
        val categoryController = CategoryController(categoryService)
        val responseEntity = categoryController.deleteCategory(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    fun testUpdateCategory() {
        val categoryService = CategoryService()
        val categoryController = CategoryController(categoryService)
        val category = Category()
        val responseEntity = categoryController.updateCategory(1, category)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }
}
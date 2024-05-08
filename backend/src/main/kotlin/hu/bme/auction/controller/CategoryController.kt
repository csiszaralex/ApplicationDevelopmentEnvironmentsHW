package hu.bme.auction.controller

import hu.bme.auction.entity.Category
import hu.bme.auction.service.CategoryService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(val categoryService: CategoryService) {
    @GetMapping()
    fun getAll(): ResponseEntity<List<Category>> {
        val lista: List<Category> = categoryService.getAll()
        if (lista.isEmpty()) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<Category> {
        val cat: Category = categoryService.getOne(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(cat)
    }

    @PostMapping()
    fun create(@RequestBody cat: Category): Category {
        return categoryService.create(cat)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody cat: Category): ResponseEntity<Category> {
        val newCat = categoryService.update(id, cat) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(newCat)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long){
        return categoryService.delete(id)
    }
}
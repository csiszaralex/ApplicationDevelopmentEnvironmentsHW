package hu.bme.auction.service

import hu.bme.auction.dao.CategoryRepository
import hu.bme.auction.entity.Category
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService(val categoryRepository: CategoryRepository) {

    fun getAll(): List<Category> {
        val cats = categoryRepository.findAll()
        cats.forEach{ cat ->
            cat.items.forEach{ item ->
                item.category = null
                item.user = null
            }
        }
        return cats
    }

    fun getOne(id: Long): Category? {
        return categoryRepository.findByIdOrNull(id)
    }

    fun create(category: Category): Category {
        return categoryRepository.save(category)
    }

    fun update(id: Long, cat: Category): Category? {
        val newCat = categoryRepository.findByIdOrNull(id) ?: return null
        cat.name?.let { newCat.name = it }
        return categoryRepository.save(newCat)
    }

    fun delete(id: Long) {
        return categoryRepository.deleteById(id)
    }

    fun getByNameOrCreate(name: String): Category? {
        val cat = categoryRepository.findByName(name)
        return cat ?: categoryRepository.save(Category(name = name))
    }
}
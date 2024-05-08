package hu.bme.auction.dao

import hu.bme.auction.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long>
package hu.bme.auction.entity

import jakarta.persistence.*

@Entity
@Table(name = "watchlists")
open class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    open var item: Item? = null

    @Column(name = "max_price", nullable = false)
    open var maxPrice: Int? = null
}
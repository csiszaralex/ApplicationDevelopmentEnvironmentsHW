package hu.bme.auction.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "watchlists")
open class Watchlist() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Positive(message = "Item must be set")
    var item: Item? = null

    constructor(user: User, item: Item) : this() {
        this.user = user
        this.item = item
    }
}
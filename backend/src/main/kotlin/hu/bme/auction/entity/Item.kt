package hu.bme.auction.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.time.OffsetDateTime

@Entity
@Table(name = "items")
open class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "title", nullable = false)
    open var title: String? = null

    @Enumerated
    @Column(name = "quality")
    open var quality: Quality? = null

    @Column(name = "payed")
    open var payed: Boolean? = null

    @Column(name = "from_date")
    open var from: OffsetDateTime? = null

    @Column(name = "to_date")
    open var to: OffsetDateTime? = null

    @Column(name = "starting_bid", nullable = false)
    open var startingBid: Int? = null

    @ManyToOne(cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "item", orphanRemoval = true)
    open var bids: MutableSet<Bid> = mutableSetOf()

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    open var feedbacks: MutableSet<Feedback> = mutableSetOf()

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var watchlists: MutableSet<Watchlist> = mutableSetOf()
}

enum class Quality {
    NEW, LIKENEW, USED
}
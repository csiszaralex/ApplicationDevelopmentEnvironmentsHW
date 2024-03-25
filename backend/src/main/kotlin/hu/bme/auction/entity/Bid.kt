package hu.bme.auction.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "bids")
open class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "issued_at")
    open var issuedAt: OffsetDateTime? = null

    @Column(name = "amount", nullable = false)
    open var amount: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    open var item: Item? = null
}
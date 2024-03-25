package hu.bme.auction.entity

import jakarta.persistence.*

@Entity
@Table(name = "feedbacks")
open class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    open var provider: User? = null

    @ManyToOne
    @JoinColumn(name = "item_id")
    open var item: Item? = null

    @Enumerated
    @Column(name = "communication")
    open var communication: FeedbackType? = null

    @Enumerated
    @Column(name = "shipping")
    open var shipping: FeedbackType? = null

    @Enumerated
    @Column(name = "description")
    open var description: FeedbackType? = null
}

enum class FeedbackType {
    POOR, AVERAGE, GOOD, EXCELLENT
}
package hu.bme.auction.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    open var id: Long? = null

    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Column(name = "full_name", nullable = false)
    open var fullName: String? = null

    @Column(name = "email", nullable = false, unique = true)
    open var email: String? = null

    @Column(name = "salt")
    open var salt: String? = null

    @Column(name = "password")
    open var password: String? = null

    @Enumerated
    @Column(name = "role")
    open var role: Role? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var items: MutableList<Item> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var bids: MutableSet<Bid> = mutableSetOf()

    @OneToMany(mappedBy = "provider", orphanRemoval = true)
    open var feedbacks: MutableSet<Feedback> = mutableSetOf()
}

enum class Role {
    ADMIN, USER
}
package hu.bme.auction.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController() {
    @GetMapping("/login")
    fun login(): String = "login"

    @GetMapping("/register")
    fun register(): String = "register"

}
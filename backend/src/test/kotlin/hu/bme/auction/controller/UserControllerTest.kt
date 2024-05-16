package hu.bme.auction.controller

import hu.bme.auction.dto.LoginUserDto
import hu.bme.auction.dto.RegisterUserDto
import hu.bme.auction.entity.User
import hu.bme.auction.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class UserController(private val userService: UserService) {

}
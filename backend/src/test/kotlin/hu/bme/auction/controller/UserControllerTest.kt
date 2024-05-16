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

@SpringBootTest
class UserController(private val userService: UserService) {
    /*
        * Register a user with the given user
        * @param user to register
        * @return ResponseEntity with status code 201
     */
    @Test
    fun testRegisterUser() {
        val user = User()
        val userService = UserService()
        val userController = UserController(userService)
        val registerUserDto = RegisterUserDto()
        val responseEntity = userController.registerUser(registerUserDto)
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
    }

    /*
        * Login a user with the given user
        * @param user to login
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testLoginUser() {
        val user = User()
        val userService = UserService()
        val userController = UserController(userService)
        val loginUserDto = LoginUserDto()
        val responseEntity = userController.loginUser(loginUserDto)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Get all users
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetUsers() {
        val userService = UserService()
        val userController = UserController(userService)
        val responseEntity = userController.getUsers()
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Get a user
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testGetUser() {
        val userService = UserService()
        val userController = UserController(userService)
        val responseEntity = userController.getUser(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Delete a user
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testDeleteUser() {
        val userService = UserService()
        val userController = UserController(userService)
        val responseEntity = userController.deleteUser(1)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    /*
        * Update a user
        * @return ResponseEntity with status code 200
     */
    @Test
    fun testUpdateUser() {
        val userService = UserService()
        val userController = UserController(userService)
        val user = User()
        val responseEntity = userController.updateUser(1, user)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

}
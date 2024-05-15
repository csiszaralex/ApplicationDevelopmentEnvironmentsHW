package hu.bme.auction.service

import hu.bme.auction.dao.UserRepository
import hu.bme.auction.dto.LoginUserDto
import hu.bme.auction.dto.RegisterUserDto
import hu.bme.auction.entity.User
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {
    fun registerUser(u: RegisterUserDto): User {
        val salt = generateSalt()
        val user = User()
        user.salt = salt
        user.email = u.email
        user.name = u.name
        user.fullName = u.fullName
        user.password = hashPassword(u.password, salt)
        return userRepository.save(user)
    }

    fun loginUser(u: LoginUserDto): User? {
        val user = userRepository.findByEmail(u.email) ?: return null
        if (user.password == hashPassword(u.password, user.salt?:"")) {
            return user
        }
        return null
    }

    private fun generateSalt(): String {
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return Base64.getEncoder().encodeToString(bytes)
    }

    private fun hashPassword(password: String?, salt: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest((password + salt).toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hash)
    }

}
package hu.bme.auction

import hu.bme.auction.repository.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class AuctionApplication(val userRepository: UserRepository){
	@GetMapping("/")
	fun hello() = userRepository.count().toString()
}

fun main(args: Array<String>) {
	runApplication<AuctionApplication>(*args)
}

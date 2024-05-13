package hu.bme.auction.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderService(private val emailSender: JavaMailSender, private val template: SimpleMailMessage){
    fun sendEmail(subject: String,text: String,targetEmail: String) {
        val message = SimpleMailMessage()
        message.subject = subject
        message.text = text
        message.setTo(targetEmail)

        emailSender.send(message)
    }

    fun sendEmailUsingTemplate(name: String,targetEmail: String) {
        val message = SimpleMailMessage(template)
        val text = template.text
        message.text = text!!.format(name)
        message.setTo(targetEmail)
        emailSender.send(message)
    }

    fun sendEmailForNewBid(itemName: String, currentPrise: Int, targetEmail: String) {
        val message = SimpleMailMessage()
        message.subject = "New bid on $itemName"
        message.text = """
            Hello, 
            There is a new bid on $itemName. The current price is $currentPrise.
        """.trimIndent()
        message.setTo(targetEmail)
        emailSender.send(message)
    }
}
package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Transport
class EmailConfirmation {
    private var context: Context
    private lateinit var name : String
    private lateinit var address : String
    private lateinit var phone : String
    private lateinit var email : String
    private lateinit var offer : String
    private lateinit var items : String
   constructor(context: Context, name: String, address: String, phone: String,
               email: String, offer: String, items: String) {
       this.context = context
       this.name = name
       this.address = address
       this.phone = phone
       this.email = email
       this.offer = offer
       this.items = items
   }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    fun sendEmailConfirmation() {


    GlobalScope.launch {

        val userEmail: String = "terpshopumd@gmail.com"
        val emailPassword = "zsdq gpgc uuob xajx"

        val property: Properties = Properties()
        property["mail.smtp.auth"] = "true"
        property["mail.smtp.starttls.enable"] = "true"
        property["mail.smtp.host"] = "smtp.gmail.com"
        property["mail.smtp.port"] = "587"

        val session: Session =
            Session.getInstance(property, object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(userEmail, emailPassword)
                }
            })
        Log.w("Email", "email is inside: $email")

        try {
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress(userEmail))
            message.setRecipient(
                Message.RecipientType.TO,
                InternetAddress(email)
            )
            message.subject = "Email Confirmation for your order"

            val imageBytes = context.resources.openRawResource(R.drawable.headerimg).readBytes()

            val base64Image = Base64.getEncoder().encodeToString(imageBytes)

            val emailContentHtml =
                "<html><body style='font-family: Arial, sans-serif;'>" +
                        "<div style='text-align: center;'>" +
                        "<img src='data:image/png;base64,$base64Image' alt='Header' style='max-width: 100%; height: auto;'>" +
                        "</div>" +
                        "<div  padding: 10px; margin: 20px;'>" +
                        "<p style='font-size: 16px; font-weight: bold; text-align: center;'>" +
                        "Thank you for your order!</p>" +
                        "</div>" +
                        "<div style='background-color: #A0EBF4; border: 2px solid #000; padding: 10px; margin: 20px; text-align: left;'>" +
                        "<p><strong> Name:</strong> $name</p>" +
                        "<p><strong> Phone:</strong> $phone</p>" +
                        "<p><strong> Email Address:</strong> $email</p>" +
                        "<p><strong> Delivery Offer:</strong> $offer</p>" +
                        "<p><strong> Item Detail:</strong> $items</p>" +
                        "</div>" +
                        "</body></html>"

            message.setContent(emailContentHtml, "text/html")

            Transport.send(message)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Email send successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: MessagingException) {
            e.printStackTrace()
            withContext(Dispatchers.Main){
                Toast.makeText(context,"Email Sending Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
}

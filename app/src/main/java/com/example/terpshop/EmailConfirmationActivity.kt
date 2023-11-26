package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Handler
import java.util.Base64

class EmailConfirmationActivity: AppCompatActivity() {

    private lateinit var emailConfirmation: EmailConfirmation
    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emailconfirmation_data)

       val items = intent.getStringExtra("itemName")!!
       val customerName = intent.getStringExtra("name")!!
       val customerAddress = intent.getStringExtra("address")!!
       val customerPhone = intent.getStringExtra("phone")!!
       val customerEmail = intent.getStringExtra("email")!!
       val customerOffer = intent.getStringExtra("offer")!!

        Log.w("Email", "email is: $customerEmail or: ")

        var emailSubject = "Email Confirmation for your order"

        val imageBytes = this@EmailConfirmationActivity.resources.openRawResource(R.drawable.headerimg).readBytes()

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
                    "<p><strong> Name:</strong> $customerName</p>" +
                    "<p><strong> Phone:</strong> $customerPhone</p>" +
                    "<p><strong> Email Address:</strong> $customerEmail</p>" +
                    "<p><strong> Delivery Offer:</strong> $customerOffer</p>" +
                    "<p><strong> Item Detail:</strong> $items</p>" +
                    "</div>" +
                    "</body></html>"


        emailConfirmation = EmailConfirmation(this, customerName, customerAddress, customerPhone, customerEmail, customerOffer, items, emailSubject, emailContentHtml)

        GlobalScope.launch(Dispatchers.IO) {

            try {
                emailConfirmation.sendEmailConfirmation()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@EmailConfirmationActivity, "Sending Email", Toast.LENGTH_SHORT).show()
                }


                withContext(Dispatchers.Main) {
                    val delayTimeMilliSec: Long = 1500
                    Handler().postDelayed({
                        val intent =
                            Intent(this@EmailConfirmationActivity, SubmissionActivity::class.java)
                        intent.putExtra("name", customerName)
                        intent.putExtra("address", customerAddress)
                        intent.putExtra("phone", customerPhone)
                        intent.putExtra("email", customerEmail)
                        intent.putExtra("offer", customerOffer)
                        intent.putExtra("itemName", items)
                        startActivity(intent)
                    }, delayTimeMilliSec)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@EmailConfirmationActivity,
                        "Sending Email Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
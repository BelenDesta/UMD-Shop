package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Base64

class DeliveredActivity: AppCompatActivity() {
    private lateinit var tv: TextView
    private lateinit var emailConfirmation: EmailConfirmation
    private lateinit var home: Button
    private lateinit var db: QueueDB
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivered_data)

        home = findViewById(R.id.homeB)
        db = QueueDB(this)

        val items = intent.getStringExtra("item")!!
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val customerItem = intent.getStringExtra("item")!!
        val driverFullname = intent.getStringExtra("fullname")!!

        val nameArr = customerName.split(": ")
        val name = nameArr[1]
        val addArr = customerAddress.split(": ")
        val address = addArr[1]
        val offerArr = customerOffer.split(": ")
        val offer = offerArr[1]

        tv = findViewById(R.id.succ)
        tv.text = "Success! You have Delivered $name's Order"

        var emailSubject = "Your order has been delivered"

        val imageBytes = this@DeliveredActivity.resources.openRawResource(R.drawable.headerimg).readBytes()

        val base64Image = Base64.getEncoder().encodeToString(imageBytes)

        val emailContentHtml =
            "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='text-align: center;'>" +
                    "<img src='data:image/png;base64,$base64Image' alt='Header' style='max-width: 100%; height: auto;'>" +
                    "</div>" +
                    "<div  padding: 10px; margin: 20px;'>" +
                    "<p style='font-size: 16px; font-weight: bold;'>" +
                    "Dear $name </p>" +
                    "<p> Your order has been delivered by $driverFullname</p>" +
                    "<p> Details about your order" +
                    "</div>" +
                    "<div style='background-color: #A0EBF4; border: 2px solid #000; padding: 10px; margin: 20px; text-align: left;'>" +
                    "<p><strong> Name:</strong> $name</p>" +
                    "<p><strong> Phone:</strong> $customerPhone</p>" +
                    "<p><strong> Email Address:</strong> $customerEmail</p>" +
                    "<p><strong> Delivery Offer:</strong> $customerOffer</p>" +
                    "<p><strong> Item Detail:</strong> $items</p>" +
                    "</div>" +
                    "<p>Thank you for shopping with TerpShop! Please rate your experience in the TerpShop app.</p>" +
                    "</body></html>"


        emailConfirmation = EmailConfirmation(this, customerName, customerAddress, customerPhone, customerEmail, customerOffer, items, emailSubject, emailContentHtml)

        GlobalScope.launch(Dispatchers.IO) {

            try {
                emailConfirmation.sendEmailConfirmation()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Log.w("name", "name:$name, address:$address, offer:$offer")
        db.deleteData(name, address, offer)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
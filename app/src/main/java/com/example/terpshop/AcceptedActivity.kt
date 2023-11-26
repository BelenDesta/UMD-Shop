package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Base64

class AcceptedActivity: AppCompatActivity() {

    private lateinit var checked: CheckBox
    private lateinit var delivered: Button
    private lateinit var tv1 : TextView
    private lateinit var tv2 : TextView
    private lateinit var emailConfirmation: EmailConfirmation


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acceptedsucc_data)

        checked = findViewById(R.id.checkbox)
        delivered = findViewById(R.id.deliverButton)

        tv1 = findViewById(R.id.success)
        tv2 = findViewById(R.id.customerNameAndAddsAndOffers)

        val items = intent.getStringExtra("item")!!
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val customerItem = intent.getStringExtra("item")!!
        val driverFullname = intent.getStringExtra("fullname")!!
        val estimateTime = intent.getStringExtra("estimateTime")!!

        val nameArr = customerName.split(":")
        val name = nameArr[1]

        tv1.text = "Success! You have accepted $name's order."
        tv2.text = "Delivering item: $items \n At the following $customerAddress \n with this $customerOffer " +
                "\n Contact the customer with: $customerPhone "


        var emailSubject = "Your order is on the way"
        //your order is picked up by name and it is on its way

        val imageBytes = this@AcceptedActivity.resources.openRawResource(R.drawable.headerimg).readBytes()

        val base64Image = Base64.getEncoder().encodeToString(imageBytes)

        val emailContentHtml =
            "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='text-align: center;'>" +
                    "<img src='data:image/png;base64,$base64Image' alt='Header' style='max-width: 100%; height: auto;'>" +
                    "</div>" +
                    "<div  padding: 10px; margin: 20px;'>" +
                    "<p style='font-size: 16px; font-weight: bold;'>" +
                    "Dear $name </p>" +
                    "<p> Your order is picked by $driverFullname and it is on its way</p>" +
                    "<p> Your order will be delived within $estimateTime </p" +
                    "<p> Details about your order" +
                    "</div>" +
                    "<div style='background-color: #A0EBF4; border: 2px solid #000; padding: 10px; margin: 20px; text-align: left;'>" +
                    "<p><strong> Name:</strong> $name</p>" +
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        delivered.setOnClickListener {
            if(checked.isChecked) {
                val intent = Intent(this, DeliveredActivity::class.java)
                intent.putExtra("name", customerName)
                intent.putExtra("address", customerAddress)
                intent.putExtra("offer", customerOffer)
                intent.putExtra("item",customerItem)
                intent.putExtra("email", customerEmail)
                intent.putExtra("phone", customerPhone)
                intent.putExtra("fullname", driverFullname)
                startActivity(intent)
            }
        }
    }
}
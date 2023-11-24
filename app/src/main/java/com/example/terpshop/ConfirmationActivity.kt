package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ConfirmationActivity: AppCompatActivity() {
    private lateinit var goback : Button
    private lateinit var submit : Button

    private lateinit var tv1 : TextView
    private lateinit var tv2 : TextView
    private lateinit var tv3 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation_data)

        goback = findViewById(R.id.goBackBtn2)
        submit = findViewById(R.id.submitBtn)

        tv1 = findViewById(R.id.listOfItems)
        tv2 = findViewById(R.id.customerNameAndAddy)
        tv3 = findViewById(R.id.price)

        val items = intent.getStringExtra("itemName")
        val customerName = intent.getStringExtra("name")
        val customerAddress = intent.getStringExtra("address")
        val customerPhone = intent.getStringExtra("phone")
        val customerEmail = intent.getStringExtra("email")
        val customerOffer = intent.getStringExtra("offer")

        tv1.text = "Items to be delivered : $items"
        tv2.text = "Delivering to $customerName at the following Address : $customerAddress"
        // update the price accordingly

        goback.setOnClickListener {
            val intent = Intent(this, ContactInfoActivity::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener {
            val intent = Intent(this, EmailConfirmationActivity::class.java)
            intent.putExtra("name", customerName)
            intent.putExtra("address", customerAddress)
            intent.putExtra("phone", customerPhone)
            intent.putExtra("email", customerEmail)
            intent.putExtra("offer", customerOffer)
            intent.putExtra("itemName", items)
            startActivity(intent)
        }
    }
}
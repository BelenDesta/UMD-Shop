package com.example.terpshop

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AcceptingActivity: AppCompatActivity() {

    private lateinit var accept : Button

    private lateinit var tv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acceptingorder_data)

        accept = findViewById(R.id.acceptButton)

        tv = findViewById(R.id.customerNameAndAddsAndOffer)

        val customerName = intent.getStringExtra("name")
        val customerAddress = intent.getStringExtra("address")
        val customerOffer = intent.getStringExtra("offer")

        tv.text = "Delivering to $customerName \n At the following Address : $customerAddress \n with this Offer : $customerOffer"

        accept.setOnClickListener {
            //if they accept
        }

    }
}
package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SubmissionActivity: AppCompatActivity() {
    private lateinit var home : Button
    private lateinit var db: QueueDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_data)

        val items = intent.getStringExtra("itemName")!!
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!

        home = findViewById(R.id.homeBtn2)
        db = QueueDB(this)


        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            db.insertData(customerName, customerAddress, customerOffer, items, customerEmail, customerPhone)
            startActivity(intent)
        }
    }
}
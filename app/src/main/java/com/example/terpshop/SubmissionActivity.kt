package com.example.terpshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class SubmissionActivity: AppCompatActivity() {
    private lateinit var home : Button
    private lateinit var db: QueueDB
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_data)

        val items = intent.getSerializableExtra("FullList", ArrayList::class.java) as? ArrayList<ItemData>
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!



        home = findViewById(R.id.homeBtn2)
        db = QueueDB(this)

        Log.w("MainActivity" , "Items Before stored in the database: + $items")



        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            db.insertData(customerName, customerAddress, customerOffer, items!!, customerEmail, customerPhone)
            Log.w("MainActivity" , "Items After stored in the database: + ${db.getItem(customerName, customerAddress,customerOffer)}")



            startActivity(intent)
        }
    }
}
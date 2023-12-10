package com.example.terpshop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var shopAsCustomer: Button
    private lateinit var becomeAShopper: Button
    private lateinit var checkOrder : Button
    private lateinit var aboutUs: Button
    private lateinit var fdb : FirebaseDatabase
    private lateinit var rateUs: Button

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // the first page

        shopAsCustomer = findViewById(R.id.customerBtn)
        becomeAShopper = findViewById(R.id.shopperBtn)
        aboutUs = findViewById(R.id.aboutUsBtn)
        checkOrder = findViewById(R.id.orderStatBtn)
        rateUs = findViewById(R.id.rateUsBtn)

        FirebaseApp.initializeApp(this)
        fdb = FirebaseDatabase.getInstance()

        rateUs.setOnClickListener {
            val intent = Intent(this, RatingActivity::class.java)
            startActivityWithTransition(intent)
        }

        aboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivityWithTransition(intent)
        }

        shopAsCustomer.setOnClickListener {
            Log.w("MainActivity", "CLICKED ON MAIN")
            val intent = Intent(this, BecomeUserActvity::class.java)
            startActivityWithTransition(intent)
        }

        becomeAShopper.setOnClickListener {
            val intent = Intent(this, BecomeShopperActivity::class.java)
            startActivityWithTransition(intent)
        }
        checkOrder.setOnClickListener {
            val intent = Intent(this, CheckOrderActivity::class.java)
            startActivityWithTransition(intent)
        }
    }

    private fun startActivityWithTransition(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
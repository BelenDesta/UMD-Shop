package com.example.terpshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var shopAsCustomer : Button
    private lateinit var becomeAShopper : Button
    private lateinit var aboutUs : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // the first page

        shopAsCustomer = findViewById(R.id.customerBtn)
        becomeAShopper = findViewById(R.id.shopperBtn)
        aboutUs = findViewById(R.id.aboutUsBtn)

        aboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        shopAsCustomer.setOnClickListener {
            val intent = Intent(this, BrowseShopActivity::class.java)
            startActivity(intent)
        }

        // DO THE SAME SET_ON_CLICK_LISTENER FOR SHOPPER BUTTON
    }
}
package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BrowseShopActivity : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var shopSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shops_data) // the second page

        continueBtn = findViewById(R.id.continueBtn1)
        shopSpinner = findViewById(R.id.shopSpinner)

        // Define an array of shops
        val shops = arrayOf(
            "Select Shop > ",
            "One Button Studio",
            "Ticket Office",
            "Union Shop",
            "University Book Center",
            "UPS Store",
            "M&T Bank",
            "North Campus Market",
            "South Campus Market",
            "Engage | ESJ"
        ) // list of shops at UMD

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shops)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        shopSpinner.adapter = adapter

        continueBtn.setOnClickListener { // Check if a shop is selected
            if (shopSpinner.selectedItemPosition > 0) { // A valid shop is selected, proceed to the next activity
                val intent = Intent(this, ShoppingDetailsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Please select a shop", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

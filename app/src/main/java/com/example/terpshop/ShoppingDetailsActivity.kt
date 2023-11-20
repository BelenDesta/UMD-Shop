package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ShoppingDetailsActivity : AppCompatActivity() {
    private lateinit var continueBtn2: Button
    private lateinit var categorySpinner: Spinner
    lateinit var itemName: EditText
    private lateinit var itemDetails: EditText
    private lateinit var relevance: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_data)

        continueBtn2 = findViewById(R.id.continueBtn2)
        categorySpinner = findViewById(R.id.categorySpinner)
        itemName = findViewById(R.id.itemName)
        itemDetails = findViewById(R.id.detailName)
        relevance = findViewById(R.id.radiogroup)

        // Define an array of shops
        val shops = arrayOf("Select Category > ",
            "Food",
            "School Supplies",
            "Hygiene Products",
            "Electronics",
            "Clothing",
            "Books",
            "Household items",
            "Beauty Products",
            "Medications",
            "Sports Equipment",
            "Office Supplies",
            "Miscellaneous") // list of shops at UMD

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shops)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        categorySpinner.adapter = adapter

        continueBtn2.setOnClickListener {
            Log.w("Test", "items name is ${itemName.text}")
            val selectedRadioButtonId = relevance.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedRadioButtonId)

            if (categorySpinner.selectedItemPosition <= 0) {
                showToast("Please select a category")
            } else if (itemName.text.isEmpty()) {
                showToast("Please enter an item name")
            } else if (itemDetails.text.isEmpty()) {
                showToast("Please enter item details")
            } else if (radioButton == null) {
                showToast("Please select relevance")
            } else {
                // Valid input, proceed to the next activity
                val intent = Intent(this, ContactInfoActivity::class.java)
                intent.putExtra("itemName", itemName.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}

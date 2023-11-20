package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

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

        tv1.text = "Items to be delivered : $items"
        tv2.text = "Delivering to $customerName at the following Address : $customerAddress"
        // update the price accordingly

        goback.setOnClickListener {
            val intent = Intent(this, ContactInfoActivity::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener {
            val intent = Intent(this, SubmissionActivity::class.java)
            startActivity(intent)
        }


    }
}
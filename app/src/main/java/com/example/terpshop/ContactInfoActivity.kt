package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactInfoActivity : AppCompatActivity() {
    private lateinit var goBack : Button
    private lateinit var continueBtn3 : Button
    lateinit var name : EditText
    lateinit var address : EditText
    private lateinit var phone : EditText
    private lateinit var email : EditText
    private lateinit var offer : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_data)

        goBack = findViewById(R.id.goBackBtn)
        continueBtn3 = findViewById(R.id.continueBtn3)

        name = findViewById(R.id.nameInfo)
        address = findViewById(R.id.addressInfo)
        phone = findViewById(R.id.phoneInfo)
        email = findViewById(R.id.emailInfo)
        offer = findViewById(R.id.offerInfo)

        val items = intent.getStringExtra("itemName")

        goBack.setOnClickListener {
            val intent = Intent(this, ShoppingDetailsActivity::class.java)
            startActivity(intent)
        }

        continueBtn3.setOnClickListener {
            Log.w("Test", "name is ${name.text}")
            Log.w("Test", "add is ${address.text}")
            Log.w("Test", "email is ${email.text}")
            if (name.text.isEmpty()) {
                showToast("Please enter your name")
            } else if (address.text.isEmpty()) {
                showToast("Please enter your address")
            } else if (phone.text.isEmpty()) {
                showToast("Please enter your phone number")
            } else if(email.text.isEmpty()){
                showToast("Please enter your email")
            } else if (offer.text.isEmpty()) {
                showToast("Please enter your offer details")
            } else {
                // Valid input, proceed to the next activity
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("address", address.text.toString())
                intent.putExtra("phone", phone.text.toString())
                intent.putExtra("email", email.text.toString())
                intent.putExtra("offer", offer.text.toString())
                intent.putExtra("itemName", items)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}
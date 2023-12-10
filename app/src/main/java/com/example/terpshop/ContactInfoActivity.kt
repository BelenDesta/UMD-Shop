package com.example.terpshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class ContactInfoActivity : AppCompatActivity() {
    private lateinit var goBack: Button
    private lateinit var continueBtn3: Button
    lateinit var name: EditText
    lateinit var address: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var offer: EditText

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

        // Retrieve intent from ConfirmationActivity
        val intentFromConfirmation = intent
        val customerName = intentFromConfirmation.getStringExtra("name")
        val customerAddress = intentFromConfirmation.getStringExtra("address")
        val customerPhone = intentFromConfirmation.getStringExtra("phone")
        val customerEmail = intentFromConfirmation.getStringExtra("email")
        val customerOffer = intentFromConfirmation.getStringExtra("offer")
        val items = intentFromConfirmation.getStringArrayListExtra("itemNames")
        val fullList = intent.getSerializableExtra("FullList") as? ArrayList<ItemData>

        Log.w("MainActivity", "Received from shopping activity to contact " + fullList)



        // Update EditText fields with information from ConfirmationActivity
        name.setText(customerName)
        address.setText(customerAddress)
        phone.setText(customerPhone)
        email.setText(customerEmail)
        offer.setText(customerOffer)

        goBack.setOnClickListener {
            val intent = Intent(this, ShoppingDetailsActivity::class.java)
            intent.putStringArrayListExtra("itemNames", items)

            intent.putExtra("name", name.text.toString())

            intent.putExtra("address", address.text.toString())
            intent.putExtra("phone", phone.text.toString())
            intent.putExtra("email", email.text.toString())
            intent.putExtra("offer", offer.text.toString())

            intent.putExtra("FullList", fullList as Serializable)
            Log.w("MainActivity", "Sent Back from Contact activity to Shopping " + fullList)


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
            } else if (email.text.isEmpty()) {
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
                intent.putExtra("itemNames", items)
                intent.putExtra("FullList", fullList as Serializable)
                Log.w("MainActivity", "Sent  from Contact activity to confirm " + fullList)


                startActivity(intent)
            }   }
        }

        private fun showToast(message: String) {
            Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
        }
    }


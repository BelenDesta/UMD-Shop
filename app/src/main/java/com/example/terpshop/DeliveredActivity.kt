package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Base64
import com.example.terpshop.ItemData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class DeliveredActivity: AppCompatActivity() {
    private lateinit var tv: TextView
    private lateinit var emailConfirmation: EmailConfirmation
    private lateinit var home: Button
    private lateinit var db: QueueDB
    private lateinit var rateExperince: Button
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivered_data)

        home = findViewById(R.id.homeB)
        rateExperince = findViewById(R.id.rateExperienceBtn2)
        db = QueueDB(this)

        val items = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            intent.getSerializableExtra("FullList") as? ArrayList<ItemData>
        } else {
            TODO("VERSION.SDK_INT < R")
        }

        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val driverFullname = intent.getStringExtra("fullname")!!


        val nameArr = customerName.split(": ")
        val name = nameArr[1]
        val addArr = customerAddress.split(": ")
        val address = addArr[1]
        val offerArr = customerOffer.split(": ")
        val offer = offerArr[1]

        tv = findViewById(R.id.succ)
        tv.text = "Success! You have Delivered $name's Order"

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE MMM yyyy hh:mm:ss a", Locale.ENGLISH)
        val formattedDateTime = currentDateTime.format(formatter)

        val newStatus = "Your order has been delivered by $driverFullname on $formattedDateTime"

        val reference = FirebaseDatabase.getInstance().getReference("customers")

        reference.child(name.trim()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val customerData = dataSnapshot.getValue(Customer::class.java)
                if (customerData != null) {
                    Log.w("UpdateStatus", "Status before updated: " + customerData.status)
                    val updateMap = mapOf("status" to newStatus)
                    reference.child(name.trim()).updateChildren(updateMap)
                        .addOnSuccessListener {
                            Log.w("UpdateStatus", "Status updated successfully:" + customerData.status )
                        }
                        .addOnFailureListener { e ->
                            Log.w("UpdateStatus", "Error updating status: $e")
                        }
                } else {
                    Log.w("UpdateStatus", "Customer does not exist")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("UpdateStatus", "Database error: ${databaseError.message}")
            }
        })


        var emailSubject = "Your order has been delivered"

        val imageBytes = this@DeliveredActivity.resources.openRawResource(R.drawable.headerimg).readBytes()

        val base64Image = Base64.getEncoder().encodeToString(imageBytes)

        var emailContentHtml =
            "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='text-align: center;'>" +
                    "<img src='data:image/png;base64,$base64Image' alt='Header' style='max-width: 100%; height: auto;'>" +
                    "</div>" +
                    "<div  padding: 10px; margin: 20px;'>" +
                    "<p style='font-size: 16px; font-weight: bold;'>" +
                    "Dear $name </p>" +
                    "<p> Your order has been delivered by $driverFullname</p>" +
                    "<p> Details about your order" +
                    "</div>" +
                    "<div style='background-color: #A0EBF4; border: 2px solid #000; padding: 10px; margin: 20px; text-align: left;'>" +
                    "<p><strong> Name:</strong> $name</p>" +
                    "<p><strong> Phone:</strong> $customerPhone</p>" +
                    "<p><strong> Email Address:</strong> $customerEmail</p>" +
                    "<p><strong> Delivery Offer:</strong> $customerOffer</p>" +
                    "<p><strong> Item Details:</strong></p>" +
                    "<ul>"

            // Iterate over the items array and add each value to the list
            // Iterate over the items array and add each value to the list
        for (item in items.orEmpty()) {
            emailContentHtml += "<li><strong>Name:</strong> ${item!!.name}, " +
                    "<strong>Category:</strong> ${item!!.category}, " +
                    Log.w("MainActivity", "CATA" + item.category)
            "<strong>Details:</strong> ${item!!.details}, " +
                    Log.w("MainActivity", "DETAILS" + item.details)

            "<strong>Relevance:</strong> ${item!!.relevance}</li>"
        }

        emailConfirmation = EmailConfirmation(this, customerName, customerAddress, customerPhone, customerEmail, customerOffer,     items as ArrayList<ItemData?>, emailSubject, emailContentHtml)

        GlobalScope.launch(Dispatchers.IO) {

            try {
                emailConfirmation.sendEmailConfirmation()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Log.w("name", "name:$name, address:$address, offer:$offer")
        Log.w("MainActivity", "From Deliver Activity" + db.getEmail(name, address, offer))
        db.deleteData(name, address, offer)

        rateExperince.setOnClickListener {
            val intent = Intent(this, RatingActivity::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.io.Serializable

import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Base64

class AcceptedActivity: AppCompatActivity() {

    private lateinit var checked: CheckBox
    private lateinit var delivered: Button
    private lateinit var tv1 : TextView
    private lateinit var tv2 : TextView
    private lateinit var emailConfirmation: EmailConfirmation
    private lateinit var db: QueueDB

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.acceptedsucc_data)
        db = QueueDB(this)


        checked = findViewById(R.id.checkbox)
        delivered = findViewById(R.id.deliverButton)

        tv2 = findViewById(R.id.customerNameAndAddsAndOffers)

        val items = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            intent.getSerializableExtra("FullList") as? ArrayList<ItemData>
        } else {
            TODO("VERSION.SDK_INT < R")
        }
        //Item is not Null
        Log.w("MainAct", "Items at Accepted Activity" + items)


        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val driverFullname = intent.getStringExtra("fullname")!!
        val estimateTime = intent.getStringExtra("estimateTime")!!

        val nameArr = customerName.split(": ")
        val name = nameArr[1]


        tv2.text = " $customerName \n $customerAddress \n  $customerOffer " +
                "\n Phone Number : $customerPhone "

        val itemListTextView: TextView = findViewById(R.id.itemListTextView)

          // Dynamically set text for each item
        if (items != null) {
            val itemListStringBuilder = StringBuilder()
            for (item in items) {
                itemListStringBuilder.append("Category: ${item?.category ?: ""}\n")
                itemListStringBuilder.append("Name: ${item?.name ?: ""}\n")
                itemListStringBuilder.append("Details: ${item?.details ?: ""}\n")
                itemListStringBuilder.append("Relevance: ${item?.relevance ?: ""}\n\n")
            }
            itemListTextView.text = itemListStringBuilder.toString()
        }

        Log.w("UpdateStatus", "name is $name")

        val newStatus = "Your order has been picked up by $driverFullname, on the way to be delivered"
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

        var emailSubject = "Your order is on the way"

        val imageBytes = this@AcceptedActivity.resources.openRawResource(R.drawable.headerimg).readBytes()

        val base64Image = Base64.getEncoder().encodeToString(imageBytes)

        var emailContentHtml =
            "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='text-align: center;'>" +
                    "<img src='data:image/png;base64,$base64Image' alt='Header' style='max-width: 100%; height: auto;'>" +
                    "</div>" +
                    "<div  padding: 10px; margin: 20px;'>" +
                    "<p style='font-size: 16px; font-weight: bold;'>" +
                    "Dear $name </p>" +
                    "<p> Your order is picked by $driverFullname and it is on its way</p>" +
                    "<p> Your order will be delived within $estimateTime </p" +
                    "<p> Details about your order" +
                    "</div>" +
                    "<div style='background-color: #A0EBF4; border: 2px solid #000; padding: 10px; margin: 20px; text-align: left;'>" +
                    "<p><strong> Name:</strong> $name</p>" +
                    "<p><strong> Phone:</strong> $customerPhone</p>" +
                    "<p><strong> Email Address:</strong> $customerEmail</p>" +
                    "<p><strong> Delivery Offer:</strong> $customerOffer</p>" +
                    "<p><strong> Item Details:</strong></p>" +
                    "<ul>"

        for (item in items!!) {
            emailContentHtml += "<li><strong>Name:</strong> ${item!!.name}, " +
                    "<strong>Category:</strong> ${item!!.category}, " +
                    "<strong>Details:</strong> ${item!!.details}, " +
                    "<strong>Relevance:</strong> ${item!!.relevance}</li>"
        }


        emailConfirmation = EmailConfirmation(this, customerName, customerAddress, customerPhone, customerEmail, customerOffer, items as ArrayList<ItemData?>, emailSubject, emailContentHtml)
        Log.w("MainAc" , "Here or not here?:"+  db.getItem(name, customerAddress , customerOffer))
        GlobalScope.launch(Dispatchers.IO) {

            try {
                emailConfirmation.sendEmailConfirmation()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        delivered.setOnClickListener {
            if(checked.isChecked) {
                val intent = Intent(this, DeliveredActivity::class.java)
                intent.putExtra("name", customerName)
                intent.putExtra("address", customerAddress)
                intent.putExtra("offer", customerOffer)

                intent.putExtra("FullList", items as Serializable)

                intent.putExtra("email", customerEmail)
                intent.putExtra("phone", customerPhone)
                intent.putExtra("fullname", driverFullname)
                startActivity(intent)
            }
        }
    }
}
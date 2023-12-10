package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable
import java.util.Locale

class CheckOrderActivity : AppCompatActivity() {

    private lateinit var orderName: TextInputLayout
    private lateinit var confiNum: TextInputLayout
    private lateinit var checkOrd : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkorderstat_data)

        orderName = findViewById(R.id.ordername)
        confiNum = findViewById(R.id.confirmation_num)
        checkOrd = findViewById(R.id.check_stat)

        checkOrd.setOnClickListener {

            val orderNameEditText: TextInputEditText = (orderName.editText as TextInputEditText?)!!
            val confiNumEditText: TextInputEditText = (confiNum.editText as TextInputEditText?)!!

            val orderNameText = orderNameEditText.text?.toString()
            val confiNumText = confiNumEditText.text?.toString()

            if (orderNameText.isNullOrBlank()) {
                showToast("Please enter your order name")
            }
            if (confiNumText.isNullOrBlank()) {
                showToast("Please enter your confirmation number")
            }
            else{
                checkUserInput(orderNameText!!, confiNumText)
            }

            val intent = Intent(this, CheckOrderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkUserInput(orderName: String, confiNum: String) {
        val reference = FirebaseDatabase.getInstance().getReference("customers")

        reference.child(orderName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val customerData = dataSnapshot.getValue(Customer::class.java)

                if (customerData != null) {
                    val storedConfirmationNum = customerData.randomNumber
                    val storedStatus = customerData.status

                    Log.w("CheckOrderActivity", "Stored Confirmation Num: $storedConfirmationNum")
                    Log.w("CheckOrderActivity", "User Confirmation Num: $confiNum")
                    Log.w("CheckOrderActivity", "Status: $storedStatus")

                    if (storedConfirmationNum == confiNum) {
                        showToast("Information matched. Proceed to the next step.")
                        val intent = Intent(this@CheckOrderActivity, OrderStatusActivity::class.java)
                        intent.putExtra("status", storedStatus)
                        startActivity(intent)
                    } else {
                        showToast("Order name or Confirmation number is not correct")
                    }
                } else {
                    showToast("Customer not found")
                }
        }
            override fun onCancelled(databaseError: DatabaseError) {
                showToast("Database error: ${databaseError.message}")
            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}
package com.example.terpshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Handler
class EmailConfirmationActivity: AppCompatActivity() {

    private lateinit var emailConfirmation: EmailConfirmation
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emailconfirmation_data)

       val items = intent.getStringExtra("itemName")!!
       val customerName = intent.getStringExtra("name")!!
       val customerAddress = intent.getStringExtra("address")!!
       val customerPhone = intent.getStringExtra("phone")!!
       val customerEmail = intent.getStringExtra("email")!!
       val customerOffer = intent.getStringExtra("offer")!!

        Log.w("Email", "email is: $customerEmail or: ")


        emailConfirmation = EmailConfirmation(this, customerName, customerAddress, customerPhone, customerEmail, customerOffer, items)

        GlobalScope.launch(Dispatchers.IO) {

            try {
                emailConfirmation.sendEmailConfirmation()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@EmailConfirmationActivity, "Sending Email", Toast.LENGTH_SHORT).show()
                }


                withContext(Dispatchers.Main) {
                    val delayTimeMilliSec: Long = 1500
                    Handler().postDelayed({
                        val intent =
                            Intent(this@EmailConfirmationActivity, SubmissionActivity::class.java)
                        intent.putExtra("name", customerName)
                        intent.putExtra("address", customerAddress)
                        intent.putExtra("phone", customerPhone)
                        intent.putExtra("email", customerEmail)
                        intent.putExtra("offer", customerOffer)
                        intent.putExtra("itemName", items)
                        startActivity(intent)
                    }, delayTimeMilliSec)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@EmailConfirmationActivity,
                        "Sending Email Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
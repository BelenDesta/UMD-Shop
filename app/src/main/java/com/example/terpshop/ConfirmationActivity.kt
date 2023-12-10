package com.example.terpshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import java.io.Serializable


class ConfirmationActivity: AppCompatActivity() {
    private lateinit var goback : Button
    private lateinit var submit : Button

    private lateinit var tv1 : TextView
    private lateinit var tv2 : TextView
    private lateinit var tv3 : TextView


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation_data)

        goback = findViewById(R.id.goBackBtn2)
        submit = findViewById(R.id.submitBtn)

        tv1 = findViewById(R.id.listOfItems)
        tv2 = findViewById(R.id.customerNameAndAddy)
        tv3 = findViewById(R.id.price)


        val fullList = intent.getSerializableExtra("FullList") as? ArrayList<ItemData>

        Log.w("MainActivity", "Recived  from Contact activity to confirm " + fullList)



        val customerName = intent.getStringExtra("name")
        val customerAddress = intent.getStringExtra("address")
        val customerPhone = intent.getStringExtra("phone")
        val customerEmail = intent.getStringExtra("email")
        val customerOffer = intent.getStringExtra("offer")
        val items = intent.getStringArrayListExtra("itemNames")!!
        var itm =""

        for (item in items){
            itm += item + "\n"
        }

        tv1.text = "Items: $itm"
        tv2.text = "Name: $customerName \n Address : $customerAddress"
        tv3.text = "Delivery Fee: $customerOffer "

        goback.setOnClickListener {
            val intent = Intent(this, ContactInfoActivity::class.java)
            intent.putExtra("name", customerName)
            intent.putExtra("address", customerAddress)
            intent.putExtra("phone", customerPhone)
            intent.putExtra("email", customerEmail)
            intent.putExtra("offer", customerOffer)
            intent.putExtra("itemNames", items)
            intent.putExtra("FullList", fullList as Serializable)

            Log.w("MainActivity", "Sent back  from confrim activity to contact " + fullList)



            startActivity(intent)
        }

        submit.setOnClickListener {
            Log.w("MainActivity", "Submit is Clicked")
            val intent = Intent(this, EmailConfirmationActivity::class.java)


            intent.putExtra("name", customerName)
            intent.putExtra("address", customerAddress)
            intent.putExtra("phone", customerPhone)
            intent.putExtra("email", customerEmail)
            intent.putExtra("offer", customerOffer)
            intent.putExtra("FullList", fullList as Serializable)
            Log.w("MainActivity", "Submit is clicked and sending to email" + fullList)

            startActivity(intent)
        }
    }
}


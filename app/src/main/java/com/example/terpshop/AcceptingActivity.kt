package com.example.terpshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class AcceptingActivity: AppCompatActivity() {

    private lateinit var accept : Button
    private lateinit var tv : TextView
    private lateinit var estimateTime: EditText

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acceptingorder_data)

        accept = findViewById(R.id.acceptButton)

        tv = findViewById(R.id.customerNameAndAddsAndOffer)
        estimateTime = findViewById(R.id.estimateTime)

        val items = intent.getSerializableExtra("FullList", ArrayList::class.java) as? ArrayList<ItemData>
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val driverFullname = intent.getStringExtra("fullname")!!

        tv.text = "Delivering to $customerName \n At the following $customerAddress \n with this $customerOffer"


        accept.setOnClickListener {
            if(estimateTime.text.isEmpty()){
                Toast.makeText(applicationContext, "Please enter the estimated time", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, AcceptedActivity::class.java)
                intent.putExtra("name", customerName)
                intent.putExtra("address", customerAddress)
                intent.putExtra("offer", customerOffer)
                intent.putExtra("email", customerEmail)
                intent.putExtra("FullList", items as Serializable)


                intent.putExtra("phone", customerPhone)
                intent.putExtra("fullname", driverFullname)
                intent.putExtra("estimateTime", estimateTime.text.toString())
                startActivity(intent)
            }
        }

    }
}
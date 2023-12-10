package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderStatusActivity : AppCompatActivity(){

    private lateinit var btn : Button
    private lateinit var txt: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_stat_data)

        btn = findViewById(R.id.hBtn)
        txt = findViewById(R.id.ord_stat)
        val status = intent.getStringExtra("status")!!


        txt.text = status

        txt.gravity = Gravity.CENTER

        btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
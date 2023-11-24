package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class BecomeShopperActivity : AppCompatActivity() {

    private lateinit var emailaddress: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: TextView
    private lateinit var database: LoginDB

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.becomeshopper_data)

        emailaddress = findViewById(R.id.emaInfo)
        password = findViewById(R.id.passwordInfo)
        loginButton = findViewById(R.id.loginn)
        signupButton = findViewById(R.id.signupInfo)
        database = LoginDB(this)


        loginButton.setOnClickListener {
            if (emailaddress.text.isEmpty()) {
                showToast("Please enter your EmailAddress")
            } else if (password.text.isEmpty()) {
                showToast("Please enter your Password")
            }else if(database.checkValid(emailaddress.text.toString(), password.text.toString())) {
                val intent = Intent(applicationContext, QueueActivity::class.java)
                startActivity(intent)
            }
            else{
                showToast("Wrong EmailAddress or Password")
            }
        }
        signupButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}
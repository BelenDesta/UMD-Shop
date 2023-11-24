package com.example.terpshop

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity: AppCompatActivity() {
    private lateinit var fullname: EditText
    private lateinit var emailaddress: EditText
    private lateinit var password: EditText
    private lateinit var confiPassword: EditText
    private lateinit var signup: Button
    private lateinit var login: TextView
    private lateinit var database: LoginDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_data)

        fullname = findViewById(R.id.fullnameSignup)
        emailaddress = findViewById(R.id.emailSignup)
        password = findViewById(R.id.passwordSignup)
        confiPassword = findViewById(R.id.confipasswordSignup)
        login = findViewById(R.id.signL)
        signup = findViewById(R.id.signupButton)

        database = LoginDB(this)

        signup.setOnClickListener {
            if (fullname.text.isEmpty()) {
                showToast("Please enter your FullName")
            } else if (emailaddress.text.isEmpty()) {
                showToast("Please enter your EmailAddress")
            } else if (password.text.isEmpty()) {
                showToast("Please enter your Password")
            } else if (confiPassword.text.isEmpty()) {
                showToast("Please enter your Confirm Password")
            } else if(password.text.toString() != confiPassword.text.toString()){
                showToast("Mismatched Confirm Password")
            } else if(database.insertUser(emailaddress.text.toString(), password.text.toString())){
                Toast.makeText(applicationContext, "SignUp successful!, please login again", Toast.LENGTH_SHORT).show()

                val intent = Intent(applicationContext, BecomeShopperActivity::class.java)
                startActivity(intent)
            } else{
                showToast("EmailAddress already exists")
            }

            login.setOnClickListener {
                val intent = Intent(applicationContext, BecomeShopperActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}
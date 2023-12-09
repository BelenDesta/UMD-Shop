package com.example.terpshop


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class BecomeUserActvity : AppCompatActivity() {

    private lateinit var emailaddress: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: TextView
    private lateinit var database: LoginDBUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w("MainActivity", "Inside become user from MAIN")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.becomeuser_data)

        emailaddress = findViewById(R.id.emaInfo_user)
        password = findViewById(R.id.passwordInfo_user)
        loginButton = findViewById(R.id.loginn_user)
        signupButton = findViewById(R.id.signupInfo_user)
        database = LoginDBUser(this)


        loginButton.setOnClickListener {
            if (emailaddress.text.isEmpty()) {
                showToast("Please enter your EmailAddress")
            } else if (password.text.isEmpty()) {
                showToast("Please enter your Password")
            }else if(database.checkValid(emailaddress.text.toString(), password.text.toString())) {
                val fullname = database.getfullname(emailaddress.text.toString(), password.text.toString())
                val intent = Intent(applicationContext, BrowseShopActivity::class.java)
                intent.putExtra("fullname", fullname)
                startActivity(intent)
            }
            else{
                showToast("Wrong EmailAddress or Password")
            }
        }
        signupButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivityUser::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "Invalid Input, $message", Toast.LENGTH_SHORT).show()
    }
}
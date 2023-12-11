package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

data class Customer(
    val name: String? = null,
    val randomNumber: String? = null,
    val status: String? = null
)
class SubmissionActivity: AppCompatActivity() {
    private lateinit var home : Button
    private lateinit var db: QueueDB
    private lateinit var itemStatus: String
    private lateinit var confiNum : TextView
    private lateinit var rateExperince: Button

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_data)

        val items = intent.getSerializableExtra("FullList") as? ArrayList<ItemData>
        val customerName = intent.getStringExtra("name")!!
        val customerAddress = intent.getStringExtra("address")!!
        val customerPhone = intent.getStringExtra("phone")!!
        val customerEmail = intent.getStringExtra("email")!!
        val customerOffer = intent.getStringExtra("offer")!!
        val randomConfig = intent.getStringExtra("randomConfi")!!

        confiNum = findViewById(R.id.confiNum)
        rateExperince = findViewById(R.id.rateExperienceBtn)

        val databaseFirebase = FirebaseDatabase.getInstance()
        val reference = databaseFirebase.getReference("customers")
        itemStatus = "Item has not been picked up yet"


        confiNum.text = "Your confirmation number is: $randomConfig \n Order Name: ${customerName.toString()}" +
                "\n Make sure to use the correct confirmation number and order name to check your order status"
        confiNum.gravity = Gravity.CENTER

        home = findViewById(R.id.homeBtn2)
        db = QueueDB(this)

        Log.w("MainActivity" , "Items Before stored in the database: + $items")

        rateExperince.setOnClickListener {
            val intent = Intent(this, RatingBarActivity::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val customerData = mapOf(
                "name" to customerName,
                "randomNumber" to randomConfig,
                "status" to itemStatus
            )
            reference.child(customerName).setValue(customerData)
            db.insertData(customerName, customerAddress, customerOffer, items!!, customerEmail, customerPhone)
            Log.w("MainActivity" , "Items After stored in the database: + ${db.getItem(customerName, customerAddress,customerOffer)}")
            startActivity(intent)
        }
    }
}
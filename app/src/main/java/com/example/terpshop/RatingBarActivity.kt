package com.example.terpshop

import RatingDatabase
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RatingBarActivity : AppCompatActivity() {

    private lateinit var experienceRatingBar: RatingBar
    private lateinit var submitRatingBtn: Button
    private lateinit var reviewCommentEditText: EditText
    private lateinit var ratingManager: RatingDatabase
    private var userRating: Float = 0.0f

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ratingbar_data)

        experienceRatingBar = findViewById(R.id.experienceRatingBar)
        submitRatingBtn = findViewById(R.id.submitRatingBtn)
        reviewCommentEditText = findViewById(R.id.reviewCommentEditText)

        ratingManager = RatingDatabase(this)

        submitRatingBtn.setOnClickListener {
            userRating = experienceRatingBar.rating

            ratingManager.saveRating(userRating, reviewCommentEditText.text.toString())

            startActivity(Intent(this, DisplayRatingsActivity::class.java))
            finish()
        }
    }
}
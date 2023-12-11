package com.example.terpshop

import RatingDatabase
import android.R.attr.defaultValue
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class DisplayRatingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_ratings)



        val goBackHome : Button = findViewById(R.id.backToHomePageButton)
        val savedRatingTextView: TextView = findViewById(R.id.savedRatingTextView)
        val savedReviewTextView: TextView = findViewById(R.id.savedReviewTextView)



        val ratingManager = RatingDatabase(this)
        val (savedRating, savedReview) = ratingManager.getRating()

        savedRatingTextView.text = "Saved Rating: $savedRating"
        savedReviewTextView.text = "Saved Review: $savedReview"

        val key = "customer rating"
        val databaseFirebase = FirebaseDatabase.getInstance()
        val reference = databaseFirebase.getReference("ratings").child(key)

        val ratingData = mapOf(
            "rating" to savedRating,
            "review" to savedReview
        )
        reference.setValue(ratingData)

        goBackHome.setOnClickListener {
            finish()
        }
    }

    }


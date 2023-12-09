package com.example.terpshop// com.example.terpshop.RatingActivity.kt

import RatingDatabase
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RatingActivity : AppCompatActivity() {
    private lateinit var experienceRatingBar: RatingBar
    private lateinit var submitRatingBtn: Button
    private lateinit var reviewCommentEditText: EditText
    private lateinit var seekBarRating : SeekBar
    private lateinit var scale : TextView
    private var userRating: Float = 0.0f
    private var recomedseekbar :Int = 0


    private lateinit var ratingManager: RatingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)


        seekBarRating = findViewById(R.id.seekBarRating);

        seekBarRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                recomedseekbar = seekBar?.progress ?: 0
            }
        })





        experienceRatingBar = findViewById(R.id.experienceRatingBar)
        submitRatingBtn = findViewById(R.id.submitRatingBtn)
        reviewCommentEditText = findViewById(R.id.reviewCommentEditText)

        ratingManager = RatingDatabase(this)

        submitRatingBtn.setOnClickListener {
            userRating = experienceRatingBar.rating

            ratingManager.saveRating(userRating, reviewCommentEditText.text.toString())

            Toast.makeText(this, "You recomend $recomedseekbar /10" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DisplayRatingsActivity::class.java))
            finish()
        }
    }
}

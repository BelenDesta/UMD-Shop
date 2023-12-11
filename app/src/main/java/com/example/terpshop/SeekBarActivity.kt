package com.example.terpshop

import RatingDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeekBarActivity : AppCompatActivity(){

    private lateinit var submitRatingBtn: Button
    private lateinit var reviewCommentEditText: EditText
    private lateinit var seekBarRating : SeekBar
    private var recomedseekbar :Int = 0

    private lateinit var ratingManager: RatingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seekbar_data)

        seekBarRating = findViewById(R.id.seekBarRating2);
        submitRatingBtn = findViewById(R.id.submitRatingBtn2)
        reviewCommentEditText = findViewById(R.id.reviewCommentEditText2)
        ratingManager = RatingDatabase(this)

        seekBarRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                recomedseekbar = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        submitRatingBtn.setOnClickListener {
            if (recomedseekbar == 0) {
                Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show()
            } else {
                ratingManager.saveRating(recomedseekbar.toFloat(),reviewCommentEditText.text.toString())
                startActivity(Intent(this, DisplayRatingsActivity::class.java))
                finish()
            }
        }
    }
}
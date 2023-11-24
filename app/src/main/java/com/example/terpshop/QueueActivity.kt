package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QueueActivity : AppCompatActivity() {
    private lateinit var db: QueueDB
    private lateinit var home : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.queue_data)

        db = QueueDB(this)
        home = findViewById(R.id.homeButton)

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val stringArr: List<String> = db.getElements()

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        if(stringArr.isNotEmpty()) {
                addRow(stringArr, tableLayout)
        }else{
            val tableRow = TableRow(this)
            val textView = TextView(this)
            textView.text = "No Person in queue"
            textView.textSize = 30f

            val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)

            layoutParams.weight = 1f
            layoutParams.gravity = android.view.Gravity.CENTER
            textView.layoutParams = layoutParams

            tableRow.addView(textView)
            tableLayout.addView(tableRow)
        }
    }

    private fun addRow(stringArr: List<String>, tableLayout: TableLayout){

        for(i in stringArr.indices){
            val element = stringArr[i].split(", ")
            val tableRow = TableRow(this)

            for(j in element.indices){
                val textView = TextView(this)
                textView.text = element[j]
                textView.setPadding(10,10,10,10)

                val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
                layoutParams.weight = 1f
                textView.layoutParams = layoutParams
                tableRow.addView(textView)
            }
            val accept = Button(this)
            accept.text = "Accept"
            accept.setBackgroundColor(Color.parseColor("#00F200"))
            accept.setOnClickListener {
                tableLayout.removeView(tableRow)
                db.deleteData(element[0])
                val customerName = element[0]
                val customerAddress = element[1]
                val customerOffer = element[2]
                val intent = Intent(this, AcceptingActivity::class.java)
                intent.putExtra("name", customerName)
                intent.putExtra("address", customerAddress)
                intent.putExtra("offer", customerOffer)
                startActivity(intent)
            }
            tableRow.addView(accept)
            tableLayout.addView(tableRow)
        }
    }
}
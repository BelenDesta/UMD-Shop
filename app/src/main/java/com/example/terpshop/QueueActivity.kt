package com.example.terpshop

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.Serializable


class QueueActivity : AppCompatActivity() {
    private lateinit var db: QueueDB
    private lateinit var tv: TextView

    private var ad : InterstitialAd? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.queue_data)



        var adUnitId : String = "ca-app-pub-3940256099942544/1033173712"
        var adRequest : AdRequest = (AdRequest.Builder( )).build( )
        var adLoad : AdLoad = AdLoad( )
        InterstitialAd.load( this, adUnitId, adRequest, adLoad )



        db = QueueDB(this)

        val stringArr: List<String> = db.getElements()

        val fullname = intent.getStringExtra("fullname")!!

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        tv = findViewById(R.id.fullname)
        tv.text = "Welcome $fullname"

        if(stringArr.isNotEmpty()) {
                addRow(stringArr, tableLayout, fullname)
        }else{
            val tableRow = TableRow(this)
            val textView = TextView(this)
            textView.text = "    No Person in queue"
            textView.textSize = 30f

            val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)

            layoutParams.weight = 1f
            layoutParams.gravity = android.view.Gravity.CENTER
            textView.layoutParams = layoutParams

            tableRow.addView(textView)
            tableLayout.addView(tableRow)
        }
       val rowSpace1 = TableRow(this)
       val rowSpace1TextView = TextView(this)
       rowSpace1.addView(rowSpace1TextView)
       tableLayout.addView(rowSpace1)

       val rowSpace2 = TableRow(this)
       val rowSpace2TextView = TextView(this)
       rowSpace2.addView(rowSpace2TextView)
       tableLayout.addView(rowSpace2)

       val rowSpace3 = TableRow(this)
       val rowSpace3TextView = TextView(this)
       rowSpace3.addView(rowSpace3TextView)
       tableLayout.addView(rowSpace3)

       val homeButtonRow = TableRow(this)
       val home = Button(this)
       home.text = "Home >"
       home.setBackgroundColor(Color.parseColor("#05D2ED"))
       home.setOnClickListener {
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       }
       homeButtonRow.addView(home)
       tableLayout.addView(homeButtonRow)
    }



    private fun addRow(stringArr: List<String>, tableLayout: TableLayout, fullname: String){

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
                //tableLayout.removeView(tableRow)
                //db.deleteData(element[0])
                val customerName = element[0]
                val customerAddress = element[1]
                val customerOffer = element[2]

                val nameArr = customerName.split(": ")
                val name = nameArr[1]

                val addressArr = element[1].split(": ")
                val address = addressArr[1]

                val offerArr = element[2].split(": ")
                val offer = offerArr[1]

                val items = db.getItem(name, address, offer)
                val email = db.getEmail(name, address, offer)
                val phone = db.getPhone(name, address, offer)

                Log.w("MainAct" , "Items: $items $email $phone")

                val intent = Intent(this, AcceptingActivity::class.java)
                intent.putExtra("name", customerName)
                intent.putExtra("address", customerAddress)
                intent.putExtra("offer", customerOffer)
                intent.putExtra("FullList", items as Serializable)

                Log.w("MainActi" , "ARE THE ITEMS HERE?: $items")
                intent.putExtra("email", email)
                intent.putExtra("phone", phone)
                intent.putExtra("fullname", fullname)
                startActivity(intent)
            }
            tableRow.addView(accept)
            tableLayout.addView(tableRow)
        }
    }


    inner class AdLoad : InterstitialAdLoadCallback( ) {
        override fun onAdFailedToLoad(p0: LoadAdError) {
            super.onAdFailedToLoad(p0)
            Log.w( "MainActivity", "ad failed to load" )
        }

        override fun onAdLoaded(p0: InterstitialAd) {
            super.onAdLoaded(p0)
            Log.w( "MainActivity", "ad loaded" )
            ad = p0
            ad!!.show( this@QueueActivity )

            var manageAd  = ManageAdShowing()
            ad!!.fullScreenContentCallback = manageAd
        }
    }

    inner class ManageAdShowing : FullScreenContentCallback( ) {
        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            Log.w( "MainActivity", "user closed the ad" )
        }

        override fun onAdClicked() {
            super.onAdClicked()
            Log.w( "MainActivity", "User clicked on the ad" )
        }

        override fun onAdImpression() {
            super.onAdImpression()
            Log.w( "MainActivity", "user has seen the ad" )
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
            Log.w( "MainActivity", "ad has been shown" )
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            Log.w( "MainActivity", "ad failed to show" )
        }
    }
}
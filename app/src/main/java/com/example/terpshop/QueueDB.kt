package com.example.terpshop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QueueDB(context: Context): SQLiteOpenHelper(context, "queuedata", null, 1) {

    private val db: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table QueueData(name TEXT primary key, address Text, offer Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists QueueData")
        onCreate(db)
    }
    fun insertData(name: String, address: String, offer: String): Boolean {
        val contVal = ContentValues()
        contVal.put("name", name)
        contVal.put("address", address)
        contVal.put("offer", offer)
        val inpt = db.insert("QueueData", null, contVal)
        return inpt != -1L
    }
    fun getElements(): List<String> {

        val stringArr= mutableListOf<String>()

        val inpt = db.rawQuery("SELECT * FROM QueueData", null)
        try {
            if (inpt.moveToFirst()) {
                do {
                    val nameInx = inpt.getColumnIndex("name")
                    val addressInx = inpt.getColumnIndex("address")
                    val offerInx = inpt.getColumnIndex("offer")

                    val name = inpt.getString(nameInx)
                    val address = inpt.getString(addressInx)
                    val offer = inpt.getString(offerInx)

                    val elements = "Name: $name, Address: $address, Offer: $offer"
                    stringArr.add(elements)
                } while (inpt.moveToNext())
            }
        }catch(e: Exception) {
            e.printStackTrace()
        }finally{
            inpt.close()
        }
    return stringArr
    }
    fun deleteData (name: String){
        db.delete("QueueData", "name=?", arrayOf(name))
    }
}
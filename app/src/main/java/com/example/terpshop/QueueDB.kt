package com.example.terpshop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class QueueDB(context: Context): SQLiteOpenHelper(context, "queueData", null, 1) {

    private val db: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Queuedata(name TEXT primary key, address Text, offer Text, item Text, email Text, phone Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Queuedata")
        onCreate(db)
    }

    fun insertData(
        name: String,
        address: String,
        offer: String,
        items: ArrayList<ItemData>,
        email: String,
        phone: String
    ): Boolean {
        val itemsJsonArray = JSONArray()
        for (item in items) {
            val itemJson = JSONObject().apply {
                put("name", item.name)
                put("category", item.category)
                put("details", item.details)
                put("relevance", item.relevance)
            }
            itemsJsonArray.put(itemJson)
        }

        val contentValues = ContentValues().apply {
            put("name", name)
            put("address", address)
            put("offer", offer)
            put("item", itemsJsonArray.toString())
            put("email", email)
            put("phone", phone)
        }

        val insertedRowId = db.insert("Queuedata", null, contentValues)
        return insertedRowId != -1L
    }


    fun getElements(): List<String> {

        val stringArr = mutableListOf<String>()

        val inpt = db.rawQuery("SELECT * FROM Queuedata", null)
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
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inpt.close()
        }
        return stringArr
    }

    fun deleteData(name: String, address: String, offer: String) {
        writableDatabase.use { db ->
            db.delete(
                "Queuedata",
                "name=? AND address=? AND offer=?",
                arrayOf(name, address, offer)
            )
        }
    }

    fun getEmail(name: String, address: String, offer: String): String? {
        var email: String? = null

        val inpt = db.rawQuery(
            "SELECT * FROM Queuedata WHERE name=? AND address=? AND offer=?",
            arrayOf(name, address, offer)
        )
        try {
            if (inpt.moveToFirst()) {
                val emailInx = inpt.getColumnIndex("email")
                email = inpt.getString(emailInx)
                Log.w("email is: ", "email chekc this: $email")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inpt.close()
        }

        return email
    }

    fun getPhone(name: String, address: String, offer: String): String? {
        var phone: String? = null

        val inpt = db.rawQuery(
            "SELECT * FROM Queuedata WHERE name=? AND address=? AND offer=?",
            arrayOf(name, address, offer)
        )
        try {
            if (inpt.moveToFirst()) {
                val phoneInx = inpt.getColumnIndex("phone")
                phone = inpt.getString(phoneInx)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inpt.close()
        }

        return phone
    }
    fun getItem(name: String, address: String, offer: String): ArrayList<ItemData?> {
        val itemList = mutableListOf<ItemData?>()

        val inpt = db.rawQuery(
            "SELECT * FROM Queuedata WHERE name=? AND address=? AND offer=?",
            arrayOf(name, address, offer)
        )
        try {
            if (inpt.moveToFirst()) {
                val itemInx = inpt.getColumnIndex("item")
                val itemsJson = inpt.getString(itemInx)

                val jsonArray = JSONArray(itemsJson)

                // Iterate over the JSON array and create ItemData objects
                for (i in 0 until jsonArray.length()) {
                    val itemObject = jsonArray.getJSONObject(i)
                    val itemName = itemObject.getString("name")
                    val itemCategory = itemObject.getString("category")
                    val itemDetails = itemObject.getString("details")
                    val itemRelevance = itemObject.getString("relevance")

                    // Create ItemData object and add it to the list
                    val itemData = ItemData(itemName, itemCategory, itemDetails, itemRelevance)
                    itemList.add(itemData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inpt.close()
        }

        return ArrayList(itemList)
    }

}
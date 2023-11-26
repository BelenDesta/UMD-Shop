package com.example.terpshop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LoginDB(context: Context):SQLiteOpenHelper(context, "logIndata", null, 1) {

    private val db: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table LogIndata(username TEXT primary key, password Text, fullname Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists LogIndata")
    }
    fun insertUser(username: String, password: String, fullname: String): Boolean {
        val contVal = ContentValues()
        contVal.put("username", username)
        contVal.put("password", password)
        contVal.put("fullname", fullname)

        val result = db.insert("LogIndata", null, contVal)
        return result != -1L
    }

    fun checkValid(username: String, password: String): Boolean {
        val vals = "select * from LogIndata where username='$username' and password='$password'"
        val userClick = db.rawQuery(vals, null)
        if(userClick.count<=0){
            userClick.close()
            return false
        }
        userClick.close()
        return true
    }
    fun getfullname(username: String, password: String): String? {
        var fullname: String? = null

        val query = "SELECT * FROM LogIndata WHERE username=? and password=?"
        val inpt = db.rawQuery(query, arrayOf(username, password))

        try {
            if (inpt.moveToFirst()) {
                val itemInx = inpt.getColumnIndex("fullname")
                fullname = inpt.getString(itemInx)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inpt.close()
        }

        return fullname
    }
}
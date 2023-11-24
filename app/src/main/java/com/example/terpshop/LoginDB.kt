package com.example.terpshop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LoginDB(context: Context):SQLiteOpenHelper(context, "userdata", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Userdata(username TEXT primary key, password Text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Userdata")
    }
    fun insertUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contVal = ContentValues()
        contVal.put("username", username)
        contVal.put("password", password)
        val inpt = db.insert("Userdata", null,contVal)
        return inpt != -1L
    }
    fun checkValid(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val vals = "select * from Userdata where username='$username' and password='$password'"
        val userClick = db.rawQuery(vals, null)
        if(userClick.count<=0){
            userClick.close()
            return false
        }
        userClick.close()
        return true
    }
}
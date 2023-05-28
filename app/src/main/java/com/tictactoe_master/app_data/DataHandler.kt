package com.tictactoe_master.app_data

import androidx.appcompat.app.AppCompatActivity

interface DataHandler {

    fun writeInt(context: AppCompatActivity, key: String, value: Int)
    fun writeFloat(context: AppCompatActivity, key: String, value: Float)
    fun writeString(context: AppCompatActivity, key: String, value: String)

    fun readInt(context: AppCompatActivity, key: String): Int
    fun readFloat(context: AppCompatActivity, key: String): Float
    fun readString(context: AppCompatActivity, key: String): String

    fun checkInt(context: AppCompatActivity, key: String): Boolean
}
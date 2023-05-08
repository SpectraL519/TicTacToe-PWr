package com.tictactoe_master.data_to_file

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.tictactoe_master.R

object FileDataHandler : DataHandler {

    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private fun setContext(context: AppCompatActivity) {
        this.sharedPref = context.getSharedPreferences(
            context.getString(R.string.app_name),
            MODE_PRIVATE
        )
        this.editor = this.sharedPref!!.edit()
    }

    override fun writeInt(context: AppCompatActivity, key: String, value: Int) {
        this.setContext(context)
        this.editor!!.putInt(key, value)
    }

    override fun writeFloat(context: AppCompatActivity, key: String, value: Float) {
        this.setContext(context)
        this.editor!!.putFloat(key, value)
    }

    override fun writeString(context: AppCompatActivity, key: String, value: String) {
        this.setContext(context)
        this.editor!!.putString(key, value)
    }

    override fun readInt(context: AppCompatActivity, key: String): Int {
        this.setContext(context)

        val value = this.sharedPref!!.getInt(key, Integer.MIN_VALUE)

        if (value == Integer.MIN_VALUE)
            throw java.lang.Exception("No data given on key = $key")

        return value
    }

    override fun readFloat(context: AppCompatActivity, key: String): Float {
        this.setContext(context)

        val value = this.sharedPref!!.getFloat(key, Float.MIN_VALUE)

        if (value == Float.MIN_VALUE)
            throw java.lang.Exception("No data given on key = $key")

        return value
    }

    override fun readString(context: AppCompatActivity, key: String): String {
        this.setContext(context)

        return sharedPref!!.getString(key, null)
            ?: throw Exception("No data given on key = $key")
    }

}
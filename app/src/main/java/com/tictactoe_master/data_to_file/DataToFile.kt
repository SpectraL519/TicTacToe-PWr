package com.tictactoe_master.data_to_file

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.tictactoe_master.R

/*
*   Before passing or reading values from file first
*   use method setContext(AppCompatActivity).
*/
object DataToFile {

    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun setContext(context: AppCompatActivity) {
        this.sharedPref = context.getSharedPreferences(
            context.getString(R.string.app_name),
            MODE_PRIVATE
        )
        this.editor = this.sharedPref!!.edit()
    }

    fun writeInt(key: String, value: Int) {
        if (this.editor == null) {
            throw Exception("No context given")
        }

        this.editor!!.putInt(key, value)
    }

    fun writeFloat(key: String, value: Float) {
        if (this.editor == null) {
            throw Exception("No context given")
        }

        this.editor!!.putFloat(key, value)
    }

    fun writeString(key: String, value: String) {
        if (this.editor == null) {
            throw Exception("No context given")
        }

        this.editor!!.putString(key, value)
    }

    fun readInt(key: String): Int {
        if (this.sharedPref == null) {
            throw Exception("No context given")
        }

        val value = this.sharedPref!!.getInt(key, Integer.MIN_VALUE)

        if (value == Integer.MIN_VALUE)
            throw java.lang.Exception("No data given on key = $key")

        return value
    }

    fun readFloat(key: String): Float {
        if (this.sharedPref == null) {
            throw Exception("No context given")
        }

        val value = this.sharedPref!!.getFloat(key, Float.MIN_VALUE)

        if (value == Float.MIN_VALUE)
            throw java.lang.Exception("No data given on key = $key")

        return value
    }

    fun readString(key: String): String {
        if (this.sharedPref == null) {
            throw Exception("No context given")
        }

        return sharedPref!!.getString(key, null)
            ?: throw Exception("No data given on key = $key")
    }

}
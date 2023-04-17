package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var onePhoneTwoPlayersBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onePhoneTwoPlayersBT = findViewById(R.id.onePhoneTwoPlayersBT)
        onePhoneTwoPlayersBT.setOnClickListener {
            val startGameIntent = Intent(this, GameActivity::class.java)
            startGameIntent.putExtra("size", "5")
            startActivity(startGameIntent)
        }
    }

}
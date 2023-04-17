package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private lateinit var oneVsOneCV: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneVsOneCV = findViewById(R.id.oneVsOneCV)
        oneVsOneCV.setOnClickListener {
            val startGameIntent = Intent(this, GameActivity::class.java)
            startGameIntent.putExtra("size", "8")
            startActivity(startGameIntent)
        }
    }

}
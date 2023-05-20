package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private lateinit var oneVsOneCV: CardView
    private lateinit var oneVsBotCV: CardView
    private lateinit var oneVsOneOnlineCV: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    private fun initView() {
        this.oneVsOneCV = findViewById(R.id.one_v_one_cv)
        this.oneVsBotCV = findViewById(R.id.one_v_bot_cv)
        this.oneVsOneOnlineCV = findViewById(R.id.one_v_one_online_cv)

        this.oneVsOneCV.setOnClickListener {
            this.startGame("1_v_1")
        }

        this.oneVsBotCV.setOnClickListener {
            // this.startGame("1_v_bot")
            Toast.makeText(this, "This functionality is not yet implemented", Toast.LENGTH_SHORT).show()
        }

        this.oneVsOneOnlineCV.setOnClickListener {
            // this.startGame("1_v_1_online")
            Toast.makeText(this, "This functionality is not yet implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGame (gameMode: String) {
        val gameIntent = Intent(this, ChooseGameTypeActivity::class.java).apply {
            putExtra("game_mode", gameMode)
        }
        startActivity(gameIntent)
    }


}
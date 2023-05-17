package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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


        this.oneVsOneCV.setOnClickListener {
            /* TODO: otwierać nową akrtywność z parametrami
            val gameIntent = Intent(this, GameActivity::class.java)
            gameIntent.putExtra("size", this.chosenBoardSize)
            gameIntent.putExtra("win_condition", "mobius")
            gameIntent.putExtra("game_mode", "point")
            gameIntent.putExtra("points_to_win", 2)
            startActivity(gameIntent)*/
        }


    }


}
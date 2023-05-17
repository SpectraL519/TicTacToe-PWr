package com.tictactoe_master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ChooseGameTypeActivity : AppCompatActivity() {
    private var chosenBoardSize = 3

    private lateinit var decreaseSizeBT: ImageView
    private lateinit var increaseSizeBT: ImageView
    private lateinit var sizeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_game_type)

        this.initView()
    }

    private fun initView() {
        this.decreaseSizeBT = findViewById(R.id.decrease_size_iv)
        this.increaseSizeBT = findViewById(R.id.increase_size_iv)
        this.sizeTV = findViewById(R.id.sizeTV)

        this.sizeTV.text = this.chosenBoardSize.toString()

        this.decreaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize > MIN_BOARD_SIZE) {
                this.chosenBoardSize--
                this.sizeTV.text = this.chosenBoardSize.toString()
            }
        }

        this.increaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize < MAX_BOARD_SIZE) {
                this.chosenBoardSize++
                this.sizeTV.text = this.chosenBoardSize.toString()
            }
        }
    }

    companion object {
        const val MIN_BOARD_SIZE = 3
        const val MAX_BOARD_SIZE = 10
    }
}
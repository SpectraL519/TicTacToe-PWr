package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView



class MainActivity : AppCompatActivity() {
    private var chosenBoardSize = 3

    private lateinit var oneVsOneCV: CardView
    private lateinit var decreaseSizeBT: ImageView
    private lateinit var increaseSizeBT: ImageView
    private lateinit var sizeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    private fun initView() {
        this.oneVsOneCV = findViewById(R.id.one_v_one_cv)
        this.decreaseSizeBT = findViewById(R.id.decrease_size_iv)
        this.increaseSizeBT = findViewById(R.id.increase_size_iv)
        this.sizeTV = findViewById(R.id.sizeTV)

        this.sizeTV.text = this.chosenBoardSize.toString()

        this.oneVsOneCV.setOnClickListener {
            val gameIntent = Intent(this, GameActivity::class.java)
            gameIntent.putExtra("size", this.chosenBoardSize)
            startActivity(gameIntent)
        }

        this.decreaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize > MainActivity.MIN_BOARD_SIZE) {
                this.chosenBoardSize--
                this.sizeTV.text = this.chosenBoardSize.toString()
            }
        }

        this.increaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize < MainActivity.MAX_BOARD_SIZE) {
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
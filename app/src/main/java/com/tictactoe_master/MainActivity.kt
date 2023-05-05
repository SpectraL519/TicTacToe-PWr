package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView

const val MIN_BOARD_SIZE = 3
const val MAX_BOARD_SIZE = 10

class MainActivity : AppCompatActivity() {

    private var chosenBoardSize = 3

    private lateinit var oneVsOneCV: CardView
    private lateinit var decreaseSizeBT: TextView
    private lateinit var increaseSizeBT: TextView
    private lateinit var sizeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneVsOneCV = findViewById(R.id.oneVsOneCV)
        decreaseSizeBT = findViewById(R.id.decreaseSizeBT)
        increaseSizeBT = findViewById(R.id.increaseSizeBT)
        sizeTV = findViewById(R.id.sizeTV)

        decreaseSizeBT.text = "<"
        increaseSizeBT.text = ">"
        sizeTV.text = chosenBoardSize.toString()

        oneVsOneCV.setOnClickListener {
            val startGameIntent = Intent(this, GameActivity::class.java)
            startGameIntent.putExtra("size", chosenBoardSize)
            startActivity(startGameIntent)
        }

        decreaseSizeBT.setOnClickListener {
            if (chosenBoardSize > MIN_BOARD_SIZE) {
                chosenBoardSize--
                sizeTV.text = chosenBoardSize.toString()
            }
        }

        increaseSizeBT.setOnClickListener {
            if (chosenBoardSize < MAX_BOARD_SIZE) {
                chosenBoardSize++
                sizeTV.text = chosenBoardSize.toString()
            }
        }
    }

}
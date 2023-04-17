package com.tictactoe_master

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var gameBoardTL: TableLayout
    private lateinit var cells: Array<Array<TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameBoardTL = findViewById(R.id.gameBoardTL)
        intent.getStringExtra("size")?.let { placeSquares(it.toInt()) }
    }

    private fun placeSquares(size: Int) {
        gameBoardTL.removeAllViews()
        cells = Array(size) { Array(size) { TextView(this) } }
        for (i in 0 until size) {
            val tableRow = TableRow(this)
            for (j in 0 until size) {
                val layoutParams = TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
                )
                layoutParams.setMargins(3, 3, 3, 3)
                cells[i][j].layoutParams = layoutParams
                cells[i][j].textSize = 22f
                cells[i][j].setTypeface(null, Typeface.BOLD)
                cells[i][j].setBackgroundColor(Color.LTGRAY)
                cells[i][j].gravity = Gravity.CENTER
                cells[i][j].setOnClickListener { cellClick(cells[i][j], i, j) }
                tableRow.addView(cells[i][j])
            }
            val rowParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            tableRow.layoutParams = rowParams
            gameBoardTL.addView(tableRow)
        }
    }

    private fun cellClick(textView: TextView, i: Int, j: Int) {
        Toast.makeText(this, "$i $j clicked", Toast.LENGTH_SHORT).show()
    }

}

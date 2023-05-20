package com.tictactoe_master

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tictactoe_master.logic.game.ClassicGame
import com.tictactoe_master.logic.game.IGame
import com.tictactoe_master.logic.game.PointGame
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition

class GameActivity : AppCompatActivity() {

    private var size = 3
    private lateinit var game: IGame

    private lateinit var pointsO: TextView
    private lateinit var pointsTie: TextView
    private lateinit var pointsX: TextView
    private lateinit var turnTV: TextView
    private lateinit var gameBoardTL: TableLayout
    private lateinit var cells: Array<Array<TextView>>
    private lateinit var nextBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.initLogic()
        this.initView()
    }

    private fun initLogic() {
        // TODO: add vs, bot, online modes handling

        this.size = intent.getIntExtra("size", 3)
        val winCondition = when (intent.getStringExtra("win_cond")) {
            "mobius" -> MobiusStripWinCondition
            else -> ClassicWinCondition
        }
        this.game = when (intent.getStringExtra("game_type")) {
            "point" -> PointGame(
                this,
                this.size,
                winCondition,
                intent.getIntExtra("points_to_win", 2)
            )
            else -> ClassicGame(this, this.size, winCondition)
        }
    }

    private fun initView() {
        this.pointsO = findViewById(R.id.points_o_tv)
        this.pointsTie = findViewById(R.id.points_tie_tv)
        this.pointsX = findViewById(R.id.points_x_tv)
        this.updateScoreView()

        this.turnTV = findViewById(R.id.turn_tv)
        this.turnTV.text = String.format("TURN: %s", this.game.state.currentPlayer.toString())

        this.gameBoardTL = findViewById(R.id.game_board_tl)

        this.gameBoardTL.removeAllViews()
        this.cells = Array(this.size) { Array(this.size) { TextView(this) } }
        for (i in 0 until this.size) {
            val tableRow = TableRow(this)
            for (j in 0 until this.size) {
                val layoutParams = TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
                )
                layoutParams.setMargins(3, 3, 3, 3)
                this.cells[i][j].layoutParams = layoutParams
                this.cells[i][j].textSize = 22f
                this.cells[i][j].setTypeface(null, Typeface.BOLD)
                this.cells[i][j].setBackgroundColor(Color.LTGRAY)
                this.cells[i][j].gravity = Gravity.CENTER
                this.cells[i][j].setOnClickListener { cellClick(cells[i][j], i, j) }
                tableRow.addView(this.cells[i][j])
            }
            val rowParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            tableRow.layoutParams = rowParams
            this.gameBoardTL.addView(tableRow)

            this.nextBT = findViewById(R.id.next_bt)
            this.nextBT.text = this.game.nextPointActionString
            this.nextBT.setOnClickListener {
                if (this.game.state.gameBlocked) {
                    // clear win mark
                    for (x in 0 until this.size) {
                        for (y in 0 until this.size)
                            this.cells[x][y].setBackgroundColor(Color.LTGRAY)
                    }

                    // clear figures
                    val coordinates = this.game.nextPointAction()
                    if (coordinates == null) {
                        for (x in 0 until this.size) {
                            for (y in 0 until this.size)
                                this.cells[x][y].text = ""
                        }
                    }
                    else {
                        for (c in coordinates)
                            this.cells[c.row][c.column].text = ""
                    }

                    this.turnTV.text =
                        String.format("TURN: %s", this.game.state.currentPlayer.toString())
                    this.nextBT.text = this.game.nextPointActionString
                    this.updateScoreView()
                }
            }
        }
    }

    private fun cellClick(textView: TextView, x: Int, y: Int) {
        if (this.game.placeFigure(x, y)) {
            val figure = this.game.state.getFigure(x, y)
            this.cells[x][y].text = figure.toString()
            this.turnTV.text = String.format("TURN: %s", figure.next().toString())

            val status = this.game.checkStatus()
            if (status.result != IWinCondition.Result.NONE) {
                if (status.result == IWinCondition.Result.O || status.result == IWinCondition.Result.X) {
                    for (c in status.coordinates) {
                        this.cells[c.row][c.column].setBackgroundColor(getColor(R.color.light_green))
                    }

                    this.nextBT.text = this.game.nextPointActionString
                }

                this.updateScoreView()
            }
        }
    }

    private fun updateScoreView() {
        this.pointsO.text = String.format(
            "%s %s",
            getString(R.string.player_o),
            this.game.state.score[IWinCondition.Result.O].toString()
        )
        this.pointsTie.text = String.format(
            "%s %s",
            getString(R.string.tie),
            this.game.state.score[IWinCondition.Result.TIE].toString()
        )
        this.pointsX.text = String.format(
            "%s %s",
            getString(R.string.player_x),
            this.game.state.score[IWinCondition.Result.X].toString()
        )
    }

    fun showWinMessage(result: IWinCondition.Result) {
        val message =
            if (result == IWinCondition.Result.TIE)
                result.toString()
            else
                "player $result won!"

        Toast.makeText(
            this,
            "Game Over: $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}

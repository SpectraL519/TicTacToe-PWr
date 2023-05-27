package com.tictactoe_master.activity

import android.widget.TextView
import com.tictactoe_master.R
import com.tictactoe_master.logic.BotHandler
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.win_condition.IWinCondition

class BotGameActivity : GameActivity() {
    private lateinit var player: Figure
    private lateinit var botHandler: BotHandler

    private lateinit var playingAsTV: TextView

    override fun initLogic() {
        super.initLogic()

        this.player = listOf(Figure.O, Figure.X).random()
        this.botHandler = BotHandler(
            winCondition = this.game.winCondition,
            player = this.player.next()
        )
    }

    override fun initView() {
        super.initView()
        this.playingAsTV = findViewById(R.id.playing_as_tv)
        this.playingAsTV.text = "Playing as: ${this.player}"

        if (this.player == Figure.X)
            this.botMovement()
    }

    override fun cellClick(textView: TextView, x: Int, y: Int) {
        if (this.game.state.currentPlayer == this.player) {
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

            if (!this.game.state.board.full())
                this.botMovement()
        }
    }

    private fun botMovement() {
        val botMove = this.botHandler.getMoveCoordinates(this.game.state.board)
        if (this.game.placeFigure(botMove.row, botMove.column)) {
            val figure = this.game.state.getFigure(botMove.row, botMove.column)
            this.cells[botMove.row][botMove.column].text = figure.toString()
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
}
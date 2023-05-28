package com.tictactoe_master.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
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

        this.player = Figure.O
        this.botHandler = BotHandler(
            winCondition = this.game.winCondition,
            player = this.player.next()
        )
    }

    override fun initView() {
        super.initView()
        this.playingAsTV = findViewById(R.id.playing_as_tv)
        this.playingAsTV.text = "Playing as: ${this.player}"

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
                            this.cells[x][y].setImageResource(android.R.color.transparent)
                    }
                } else {
                    for (c in coordinates)
                        this.cells[c.row][c.column].setImageResource(android.R.color.transparent)
                }

                this.turnTV.text =
                    String.format("TURN: %s", this.game.state.currentPlayer.toString())
                this.nextBT.text = this.game.nextPointActionString
                this.updateScoreView()

                if (this.player == Figure.X)
                    this.botMovement()
            }
        }

        if (this.player == Figure.X)
            this.botMovement()
    }

    override fun cellClick(imageView: ImageView, x: Int, y: Int) {
        if (this.game.state.currentPlayer == this.player) {
            if (this.game.placeFigure(x, y)) {
                val figure = this.game.state.getFigure(x, y)
                this.cells[x][y].setImageResource(figure.getImageResource())
                checkDimensions()
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
            this.cells[botMove.row][botMove.column].setImageResource(figure.getImageResource())
            checkDimensions()
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
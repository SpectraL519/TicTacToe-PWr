package com.tictactoe_master.logic.game

import com.tictactoe_master.GameActivity
import com.tictactoe_master.logic.utils.*
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition


class ClassicGame
    constructor(
        private val context: GameActivity,
        private val _boardSize: Int,
        private val _winCondition: IWinCondition = ClassicWinCondition)
    : IGame {

    private var _state: GameState = GameState(GameBoard(this._boardSize))
    override val state: GameState
        get() = this._state

    override val nextPointActionString: String
        get() = "PLAY AGAIN"

    override fun placeFigure (x: Int, y: Int) : Boolean {
        if (this._state.gameFinished)
            return false

        if (this._state.gameBlocked)
            return false

        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer

            val result = this.checkStatus().result
            val finished = (result != IWinCondition.Result.NONE)
            val score: MutableMap<IWinCondition.Result, Int> = this._state.score
            if (finished)
                score[result] = score.getOrDefault(result, -1) + 1

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
                blocked = finished,
                finished = finished,
                score = score
            )

            if (finished)
                this.context.showWinMessage(result)
            return true
        }

        return false
    }

    override fun checkStatus() : Status {
        return this._winCondition.check(this._state.board)
    }

    override fun nextPointAction() : List<Coordinates>? {
        this.reset()
        return null
    }

    override fun reset() {
        val board = this._state.board
        board.clear()

        this._state.update(
            board = board,
            currentPlayer = Figure.O,
            blocked = false,
            finished = false
        )
    }
}
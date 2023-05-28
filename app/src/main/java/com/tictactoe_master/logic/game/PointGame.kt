package com.tictactoe_master.logic.game

import com.tictactoe_master.activity.GameActivity
import com.tictactoe_master.logic.utils.*
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition


class PointGame
    constructor(
        private val context: GameActivity,
        private val _boardSize: Int,
        private val _winCondition: IWinCondition = ClassicWinCondition,
        private val _points: Int = 3
    )
    : IGame {

    private var _state: GameState = GameState(GameBoard(this._boardSize))
    private var _currentStatus: Status = Status()

    override val state: GameState
        get() = this._state

    override val winCondition: IWinCondition
        get() = this._winCondition

    override val nextPointActionString: String
        get() = if (this._state.gameFinished) "PLAY AGAIN" else "CONTINUE"


    override fun placeFigure (x: Int, y: Int): Boolean {
        if (this._state.gameFinished)
            return false

        if (this._state.gameBlocked)
            return false

        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer

            this._currentStatus = this.checkStatus()
            val pointGained = (this._currentStatus.result != IWinCondition.Result.NONE)
            val score: MutableMap<IWinCondition.Result, Int> = this._state.score
            if (pointGained)
                score[this._currentStatus.result] = score.getOrDefault(this._currentStatus.result, -1) + 1
            val finished: Boolean = (
                score[IWinCondition.Result.O] == this._points ||
                score[IWinCondition.Result.X] == this._points
            )

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
                blocked = (pointGained || this._currentStatus.result == IWinCondition.Result.TIE),
                finished = finished,
                score = score,
            )

            if (finished) {
                this.context.gameOver(this._currentStatus.result, _winCondition, _points)
                this.context.showWinMessage(this._currentStatus.result)
            }
            return true
        }

        return false
    }

    override fun checkStatus(): Status {
        return this._winCondition.check(this._state.board)
    }

    override fun nextPointAction(): List<Coordinates>? {
        if (this._state.gameFinished) {
            this.reset()
            return null
        }

        var coordinates: List<Coordinates>? = null
        if (this._currentStatus.result != IWinCondition.Result.NONE) {
            val board = this._state.board
            if (this._currentStatus.result == IWinCondition.Result.TIE)
                board.clear()
            else {
                coordinates = this._currentStatus.coordinates
                for (c in this._currentStatus.coordinates)
                    board[c.row][c.column] = Figure.EMPTY
            }

            this._state.update(
                board = board,
                blocked = false
            )
        }

        this._currentStatus = Status()
        return coordinates
    }

    override fun reset() {
        val board = this._state.board
        board.clear()

        this._state.update(
            board = board,
            currentPlayer = Figure.O,
            blocked = false,
            finished = false,
            score = GameState.DEFAULT_SCORE
        )
    }
}
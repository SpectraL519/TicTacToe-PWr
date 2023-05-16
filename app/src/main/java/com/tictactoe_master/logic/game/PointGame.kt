package com.tictactoe_master.logic.game

import android.util.Log
import android.widget.Toast
import com.tictactoe_master.logic.utils.*
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition



class PointGame
    constructor(
        private val _boardSize: Int,
        private val _winCondition: IWinCondition = ClassicWinCondition,
        private val _points: Int = 3
    )
    : IGame {

    private var _state: GameState =
        GameState(
            _board = GameBoard(this._boardSize),
            _score = GameState.DEFAULT_SCORE
        )

    private var _currentStatus: Status = Status()

    override val state: GameState
        get() = this._state

    override val nextPointActionString: String
        get() = if (this._state.gameFinished) "PLAY AGAIN" else "CONTINUE"


    override fun placeFigure (x: Int, y: Int): Boolean {
        if (this._state.gameFinished)
            return false

        if (this._state.gameBlocked)
            return false

        val board = this._state.board
        if (
            this._currentStatus.result == IWinCondition.Result.NONE &&
            board[x][y] == Figure.EMPTY
        ) {
            board[x][y] = this._state.currentPlayer;

            this._currentStatus = this.checkStatus()
            val pointGained = (this._currentStatus.result != IWinCondition.Result.NONE)
            val score: MutableMap<IWinCondition.Result, Int> = this._state.score!!
            val finished: Boolean = (
                this._state.score!![IWinCondition.Result.O] == this._points ||
                this._state.score!![IWinCondition.Result.X] == this._points
            )

            if (pointGained)
                score[this._currentStatus.result] = score[this._currentStatus.result]?.plus(1)!!

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
                blocked = pointGained,
                finished = finished,
                score = score,
            )

            return true;
        }

        return false;
    }

    override fun checkStatus(): Status {
        return this._winCondition.check(this._state.board);
    }

    override fun nextPointAction(): List<Coordinates>? {
        if (this._state.gameFinished) {
            this.reset()
            Log.d("nextPointAction", "reset")
            return null
        }

        else if (this._currentStatus.result != IWinCondition.Result.NONE) {
            val board = this._state.board
            if (this._currentStatus.result == IWinCondition.Result.TIE)
                board.clear()
            else
                for (coordinates in this._currentStatus.coordinates)
                    board[coordinates.row][coordinates.column] = Figure.EMPTY

            this._state.update(
                board = board,
                blocked = false
            )
        }

        val coordinates = this._currentStatus.coordinates
        this._currentStatus = Status()
        Log.d("nextPointAction", "continue")
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
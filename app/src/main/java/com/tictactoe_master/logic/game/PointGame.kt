package com.tictactoe_master.logic.game

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status
import com.tictactoe_master.logic.win_condition.IWinCondition



class PointGame
    constructor(
        private val _boardSize: Int,
        private val _winCondition: IWinCondition,
        private val _points: Int = 3
    )
    : IGame {

    private var _state: GameState =
        GameState(
            _board = GameBoard(this._boardSize),
            _score = GameState.DEFAULT_SCORE
        )

    override val state: GameState
        get() = this._state

    private val board: GameBoard = GameBoard(this._boardSize)
    private var finished: Boolean = false

    override fun placeFigure (x: Int, y: Int): Boolean {
        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer;

            val status: Status = this.checkStatus()
            val pointGained = (status.result != IWinCondition.Result.NONE)
            val score: MutableMap<IWinCondition.Result, Int> = this._state.score!!
            val finished: Boolean = (score.maxBy { it.value }.value == this._points)

            if (pointGained) {
                score[status.result] = score[status.result]?.plus(1)!!

                if (!finished) {
                    if (status.result == IWinCondition.Result.TIE)
                        board.clear()
                    else
                        for (coordinates in status.coordinates)
                            board[coordinates.row][coordinates.column] = Figure.EMPTY
                }
            }

            // this._state = this._state.copy(
            this._state.update(
                _board = board,
                _currentPlayer = this._state.currentPlayer.next(),
                _finished = finished,
                _score = score
            )

            return true;
        }

        return false;
    }

    override fun checkStatus(): Status {
        return this._winCondition.check(this.board);
    }

    override fun reset() {
        val board = this._state.board
        board.clear()

        this._state = this._state.copy(
            _board = board,
            _currentPlayer = Figure.O,
            _finished = false,
            _score = GameState.DEFAULT_SCORE
        )
    }
}
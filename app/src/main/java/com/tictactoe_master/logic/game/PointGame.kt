package com.tictactoe_master.logic.game

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.win_condition.IWinCondition



class PointGame
    constructor(
        private val _boardSize: Int,
        private val _winCondition: IWinCondition
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

            val status: IWinCondition.Result = this.checkStatus()
            val finished: Boolean = (status != IWinCondition.Result.NONE)

            val score: MutableMap<IWinCondition.Result, Int> = this._state.score!!
            if (finished) {
                score[status] = score[status]?.plus(1)!!
                // TODO: clear the point area (row, col, diag)
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

    override fun checkStatus(): IWinCondition.Result {
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
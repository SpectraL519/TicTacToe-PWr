package com.tictactoe_master.logic.utils

import com.tictactoe_master.logic.win_condition.IWinCondition


data class GameState
    constructor(
        private var _board: GameBoard,
        private var _currentPlayer: Figure = Figure.O,
        private var _blocked: Boolean = false,
        private var _finished: Boolean = false,
        private var _score: MutableMap<IWinCondition.Result, Int> = DEFAULT_SCORE,
    ) {

    val board get() = this._board
    val boardSize get() = this._board.size()
    fun getFigure (x: Int, y: Int): Figure = this._board[x][y]

    val currentPlayer get() = this._currentPlayer
    val gameBlocked get() = this._blocked
    val gameFinished get() = this._finished

    val score get() = this._score
    fun getScore (result: IWinCondition.Result): Int {
        return this._score.getOrDefault(result, 0)
    }

    fun update (
        board: GameBoard = this._board,
        currentPlayer: Figure = this._currentPlayer,
        blocked: Boolean = this._blocked,
        finished: Boolean = this._finished,
        score: MutableMap<IWinCondition.Result, Int> = this._score,
    ) {
        this._board = board
        this._currentPlayer = currentPlayer
        this._blocked = blocked
        this._finished = finished
        this._score = score
    }

    companion object {
        val DEFAULT_SCORE: MutableMap<IWinCondition.Result, Int>
            get() = mutableMapOf(
                IWinCondition.Result.O to 0,
                IWinCondition.Result.X to 0,
                IWinCondition.Result.TIE to 0
            )
    }
}

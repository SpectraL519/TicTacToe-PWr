package com.tictactoe_master.logic.game

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.GameState
import com.tictactoe_master.logic.utils.Status
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition



class ClassicGame
    constructor(
        private val _boardSize: Int,
        private val _winCondition: IWinCondition = ClassicWinCondition)
    : IGame {

    private var _state: GameState = GameState(GameBoard(this._boardSize))
    override val state: GameState
        get() = this._state

    override fun placeFigure (x: Int, y: Int) : Boolean {
        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer;

            // this._state = this._state.copy(
            this._state.update(
                _board = board,
                _currentPlayer = this._state.currentPlayer.next(),
                _finished = (this.checkStatus().result != IWinCondition.Result.NONE)
            )

            return true;
        }

        return false;
    }

    override fun checkStatus() : Status {
        return this._winCondition.check(this._state.board);
    }

    override fun reset() {
        val board = this._state.board
        board.clear()

        // this._state = this._state.copy(
        this._state.update(
            _board = board,
            _currentPlayer = Figure.O,
            _finished = false
        )
    }
}
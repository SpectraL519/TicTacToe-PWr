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

    override val nextPointActionString: String
        get() = "PLAY AGAIN"

    override fun placeFigure (x: Int, y: Int) : Boolean {
        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer;

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
                finished = (this.checkStatus().result != IWinCondition.Result.NONE)
            )

            return true;
        }

        return false;
    }

    override fun checkStatus() : Status {
        return this._winCondition.check(this._state.board);
    }

    override fun nextPointAction() {
        this.reset()
    }

    override fun reset() {
        val board = this._state.board
        board.clear()

        this._state.update(
            board = board,
            currentPlayer = Figure.O,
            finished = false
        )
    }
}
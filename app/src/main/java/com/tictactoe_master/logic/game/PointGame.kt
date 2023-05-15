package com.tictactoe_master.logic.game

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
        val board = this._state.board
        if (
            this._currentStatus.result == IWinCondition.Result.NONE &&
            board[x][y] == Figure.EMPTY
        ) {
            board[x][y] = this._state.currentPlayer;

            this._currentStatus = this.checkStatus()
            val pointGained = (this._currentStatus.result != IWinCondition.Result.NONE)
            val score: MutableMap<IWinCondition.Result, Int> = this._state.score!!
            val finished: Boolean = (score.maxBy { it.value }.value == this._points)

            if (pointGained)
                score[this._currentStatus.result] = score[this._currentStatus.result]?.plus(1)!!

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
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

    override fun nextPointAction() {
        if (this._state.gameFinished)
            this.reset()
        else if (this._currentStatus.result != IWinCondition.Result.NONE) {
            val board = this._state.board
            if (this._currentStatus.result == IWinCondition.Result.TIE)
                board.clear()
            else
                for (coordinates in this._currentStatus.coordinates)
                    board[coordinates.row][coordinates.column] = Figure.EMPTY

            this._state.update(board = board)
        }

        this._currentStatus = Status()
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
package com.tictactoe_master.logic

import com.tictactoe_master.logic.win_condition.IWinCondition



class ClassicGame
    constructor(
        private val boardSize: Int,
        private val winCondition: IWinCondition)
    : IGame {

    private var board: GameBoard = GameBoard(this.boardSize)


    override fun place(x: Int, y: Int, figure: Figure) : Boolean {
        if (this.board[x][y] == Figure.EMPTY) {
            this.board[x][y] = figure;
            return true;
        }

        return false;
    }

    override fun checkStatus() : IWinCondition.Result {
        return this.winCondition.check(this.board);
    }

    override fun reset() {
        this.board = GameBoard(this.boardSize)
    }
}
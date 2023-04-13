package com.tictactoe_master.logic



class Game
    constructor(private val boardSize: Int,
                private val winCondition: IWinCondition) {

    private var board: GameBoard = GameBoard(this.boardSize)

    fun place(x: Int, y: Int, state: Figure) : Boolean {
        if (this.board[x][y] == Figure.EMPTY) {
            this.board[x][y] = state;
            return true;
        }

        return false;
    }

    fun checkState() {
        if (this.winCondition.isGameFinished(this.board) != Figure.NONE) {
            // TODO: Game ended
        }
    }

    fun resetBoard() {
        this.board = GameBoard(this.boardSize)
    }
}
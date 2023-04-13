package com.tictactoe_master.logic



interface IWinCondition {
    fun isGameFinished (board: GameBoard) : Figure {
        return Figure.NONE
    }
}
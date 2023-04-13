package com.tictactoe_master.logic



data class GameBoard constructor(private val size: Int) {
    private var board: Array<Array<Figure>> =
        Array<Array<Figure>>(this.size) { Array<Figure>(this.size) { Figure.EMPTY } }
    operator fun get(index: Int): Array<Figure> = this.board[index]
}
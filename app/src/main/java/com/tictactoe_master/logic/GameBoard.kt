package com.tictactoe_master.logic



data class GameBoard constructor(private val size: Int) {
    private var board: Array<Array<Figure>> =
        Array<Array<Figure>>(this.size) { Array<Figure>(this.size) { Figure.EMPTY } }

    fun size(): Int = this.size

    fun isFull(): Boolean = this.board.all { row -> row.all { it != Figure.EMPTY } }

    operator fun get(index: Int): Array<Figure> = this.board[index]

    fun row(index: Int): Array<Figure> = this.board[index]

    fun column(index: Int): Array<Figure> =
        Array<Figure>(this.size) { this.board[it][index] }

    fun diagonal(right: Boolean = true): Array<Figure> {
        if (right)
            return Array<Figure>(this.size) { this.board[it][it] }
        return Array<Figure>(this.size) { this.board[it][this.size - 1 - it] }
    }

    fun clear() {
        this.board = Array<Array<Figure>>(this.size) { Array<Figure>(this.size) { Figure.EMPTY } }
    }
}
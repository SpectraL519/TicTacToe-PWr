package com.tictactoe_master.logic



data class GameBoard constructor(private val size: Int) {
    private var board: Array<IntArray> = Array<IntArray>(this.size) { IntArray(this.size) { CellState.EMPTY } }
    operator fun get(index: Int): IntArray = this.board[index]
}
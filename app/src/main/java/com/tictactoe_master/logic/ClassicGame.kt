package com.tictactoe_master.logic



class ClassicGame constructor(private val boardSize: Int) : IGame{
    private var board: GameBoard = GameBoard(this.boardSize)

    override fun initBoard() {
        TODO("Not yet implemented")
    }

    override fun placeX(x: Int, y: Int) {
        TODO("Not yet implemented")
    }

    override fun placeO(x: Int, y: Int) {
        TODO("Not yet implemented")
    }

    override fun checkState() {
        TODO("Not yet implemented")
    }

    override fun resetBoard() {
        TODO("Not yet implemented")
    }
}
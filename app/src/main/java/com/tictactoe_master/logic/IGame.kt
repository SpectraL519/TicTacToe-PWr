package com.tictactoe_master.logic



interface IGame {
    fun initBoard();
    fun placeX(x: Int, y:Int);
    fun placeO(x: Int, y:Int);
    fun checkState();
    fun resetBoard();
}
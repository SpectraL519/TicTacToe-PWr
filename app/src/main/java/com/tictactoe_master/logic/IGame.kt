package com.tictactoe_master.logic

import com.tictactoe_master.logic.win_condition.IWinCondition


interface IGame {
    fun place(x: Int, y: Int, figure: Figure) : Boolean;
    fun checkStatus() : IWinCondition.Result;
    fun reset();
}
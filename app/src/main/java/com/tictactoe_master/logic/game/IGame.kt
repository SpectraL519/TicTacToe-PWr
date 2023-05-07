package com.tictactoe_master.logic.game

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.Status
import com.tictactoe_master.logic.win_condition.IWinCondition


interface IGame {
    val state: GameState

    fun placeFigure (x: Int, y: Int) : Boolean;
    fun checkStatus() : Status;
    fun reset();
}
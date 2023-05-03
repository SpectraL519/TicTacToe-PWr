package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.GameBoard



interface IWinCondition {
    fun check (board: GameBoard) : Result

    enum class Result {
        NONE, TIE, X, O
    }
}
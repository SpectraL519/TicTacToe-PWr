package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status



interface IWinCondition {
    fun check (board: GameBoard) : Status

    enum class Result {
        NONE, TIE, X, O
    }
}
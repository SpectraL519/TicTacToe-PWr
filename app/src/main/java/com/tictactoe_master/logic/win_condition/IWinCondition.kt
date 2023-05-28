package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status

interface IWinCondition {
    fun check (board: GameBoard) : Status
    fun getEvaluation (board: GameBoard, player: Figure): Long

    enum class Result {
        NONE, TIE, X, O
    }
}
package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard



object ClassicWinCondition : IWinCondition {
    // singleton class
    
    override fun check(board: GameBoard): IWinCondition.Result {
        if (board.diagonal().all { it == Figure.O })
            return IWinCondition.Result.O

        if (board.diagonal().all { it == Figure.X })
            return IWinCondition.Result.X

        if (board.diagonal(false).all { it == Figure.O })
            return IWinCondition.Result.O

        if (board.diagonal(false).all { it == Figure.X })
            return IWinCondition.Result.X

        for (i: Int in 0 until board.size()) {
            if (board.row(i).all { it == Figure.O })
                return IWinCondition.Result.O

            if (board.row(i).all { it == Figure.X })
                return IWinCondition.Result.X

            if (board.column(i).all { it == Figure.O })
                return IWinCondition.Result.O

            if (board.column(i).all { it == Figure.X })
                return IWinCondition.Result.X
        }

        if (board.isFull())
            return IWinCondition.Result.TIE

        return IWinCondition.Result.NONE
    }
}
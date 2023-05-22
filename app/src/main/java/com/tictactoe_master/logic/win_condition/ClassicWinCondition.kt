package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status


object ClassicWinCondition : IWinCondition {
    override fun check (board: GameBoard): Status {
        val boardSize: Int = board.size()
        
        if (board.diagonal().all { it == Figure.O })
            return Status(
                result = IWinCondition.Result.O,
                coordinates = List<Coordinates>(boardSize) {
                    index -> Coordinates(index, index)
                }
            )

        if (board.diagonal().all { it == Figure.X })
            return Status(
                result = IWinCondition.Result.X,
                coordinates = List<Coordinates>(boardSize) {
                    index -> Coordinates(index, index)
                }
            )

        if (board.diagonal(false).all { it == Figure.O })
            return Status(
                result = IWinCondition.Result.O,
                coordinates = List<Coordinates>(boardSize) {
                    index -> Coordinates(index, boardSize - 1 - index)
                }
            )

        if (board.diagonal(false).all { it == Figure.X })
            return Status(
                result = IWinCondition.Result.X,
                coordinates = List<Coordinates>(boardSize) {
                    index -> Coordinates(index, boardSize - 1 - index)
                }
            )

        for (i: Int in 0 until boardSize) {
            if (board.row(i).all { it == Figure.O })
                return Status(
                    result = IWinCondition.Result.O,
                    coordinates = List<Coordinates>(boardSize) {
                        index -> Coordinates(i, index)
                    }
                )

            if (board.row(i).all { it == Figure.X })
                return Status(
                    result = IWinCondition.Result.X,
                    coordinates = List<Coordinates>(boardSize) {
                        index -> Coordinates(i, index)
                    }
                )

            if (board.column(i).all { it == Figure.O })
                return Status(
                    result = IWinCondition.Result.O,
                    coordinates = List<Coordinates>(boardSize) {
                        index -> Coordinates(index, i)
                    }
                )

            if (board.column(i).all { it == Figure.X })
                return Status(
                    result = IWinCondition.Result.X,
                    coordinates = List<Coordinates>(boardSize) {
                        index -> Coordinates(index, i)
                    }
                )
        }

        if (board.full())
            return Status(result = IWinCondition.Result.TIE)

        return Status(result = IWinCondition.Result.NONE)
    }

    override fun getEvaluation(board: GameBoard): Int {
        TODO("Not yet implemented")
    }
}
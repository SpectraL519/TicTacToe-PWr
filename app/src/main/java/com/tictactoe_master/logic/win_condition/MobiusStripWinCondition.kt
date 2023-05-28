package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status

object MobiusStripWinCondition : IWinCondition {
    override fun check(board: GameBoard): Status {
        val classicWinStatus: Status = ClassicWinCondition.check(board)
        if (
            classicWinStatus.result == IWinCondition.Result.O ||
            classicWinStatus.result == IWinCondition.Result.X
        ) return classicWinStatus

        val stripO = checkStrip(board, Figure.O)
        if (stripO != null)
            return Status(
                result = IWinCondition.Result.O,
                coordinates = stripO
            )

        val stripX = checkStrip(board, Figure.X)
        if (stripX != null)
            return Status(
                result = IWinCondition.Result.X,
                coordinates = stripX
            )

        return classicWinStatus // (TIE, null) or (NONE, null)
    }

    override fun getEvaluation(board: GameBoard, player: Figure): Long {
        return ClassicWinCondition.getEvaluation(board, player)
    }

    private fun checkStrip (
        board: GameBoard,
        figure: Figure
    ): List<Coordinates>? {
        val boardSize = board.size()

        for (row: Int in 0 until boardSize) {
            var lastConsecutiveFigure: Int = -1

            for (column: Int in 0 until boardSize) {
                if (board[row][column] == figure)
                    lastConsecutiveFigure = column
                else break
            }

            println(String.format("row=%d, lastConsecutive=%d", row, lastConsecutiveFigure))
            if (lastConsecutiveFigure == -1)
                continue

            var stripFound = true
            for (column: Int in lastConsecutiveFigure + 1 until boardSize) {
                if (board[boardSize - 1 - row][column] != figure) {
                    stripFound = false
                    break
                }
            }

            if (stripFound) {
                val coordinatesA = List(lastConsecutiveFigure + 1) {
                    index -> Coordinates(row, index)
                }
                val coordinatesB = List(boardSize - 1 - lastConsecutiveFigure) {
                    index -> Coordinates(boardSize - 1 - row, boardSize - 1 - index)
                }

                return (coordinatesA + coordinatesB).sorted()
            }
        }

        return null
    }
}
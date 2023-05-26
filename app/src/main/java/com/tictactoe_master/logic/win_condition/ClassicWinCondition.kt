package com.tictactoe_master.logic.win_condition

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.utils.Status
import kotlin.math.pow


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

    override fun getEvaluation(board: GameBoard, player: Figure): Long {
        val boardSize = board.size()
        val maxEval = 10.0.pow(boardSize.toDouble()).toLong()
        val opponent = player.next()

        val playerCounts = Array<Int>(boardSize) { 0 }
        val opponentCounts = Array<Int>(boardSize) { 0 }
        var evaluation: Long = 0

        var playerCount = 0
        var opponentCount = 0

        val diagonalRight = board.diagonal(right = true)
        for (figure in diagonalRight)
            if (figure == player)
                playerCount++
            else if (figure == opponent)
                opponentCount++

        if (playerCount == boardSize)
            return maxEval
        if (opponentCount == boardSize)
            return -maxEval

        playerCounts[playerCount]++
        opponentCounts[opponentCount]++

        playerCount = 0
        opponentCount = 0

        val diagonalLeft = board.diagonal(right = false)
        for (figure in diagonalLeft)
            if (figure == player)
                playerCount++
            else if (figure == opponent)
                opponentCount++

        if (playerCount == boardSize)
            return maxEval
        if (opponentCount == boardSize)
            return -maxEval

        playerCounts[playerCount]++
        opponentCounts[opponentCount]++

        for (idx: Int in 0 until boardSize) {
            // idx-th row
            playerCount = 0
            opponentCount = 0

            val row = board.row(idx)
            for (figure in row)
                if (figure == player)
                    playerCount++
                else if (figure == opponent)
                    opponentCount++

            if (playerCount == boardSize)
                return maxEval
            if (opponentCount == boardSize)
                return -maxEval

            playerCounts[playerCount]++
            opponentCounts[opponentCount]++

            // idx-th column
            playerCount = 0
            opponentCount = 0

            val column = board.column(idx)
            for (figure in column)
                if (figure == player)
                    playerCount++
                else if (figure == opponent)
                    opponentCount++

            if (playerCount == boardSize)
                return maxEval
            if (opponentCount == boardSize)
                return -maxEval

            playerCounts[playerCount]++
            opponentCounts[opponentCount]++

            // update eval
            evaluation += 25 * (2.0.pow(idx).toLong() - 1) * (playerCounts[idx] - opponentCounts[idx])
        }

        return evaluation
    }
}
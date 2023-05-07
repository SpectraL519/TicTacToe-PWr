package com.tictactoe_master

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition
import org.junit.Assert
import org.junit.Test


class WinConditionTest {
    @Test
    fun classicWinConditionTest() {
        val winCondition: IWinCondition = ClassicWinCondition

        val size = 3
        val board = GameBoard(size)

        var status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.NONE, status.result)
        Assert.assertEquals(emptyList<Coordinates>(), status.coordinates)

        for (i in 0 until size) {
            board.clear()
            board.row(i).fill(Figure.O)
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.O, status.result)
            Assert.assertEquals(
                List<Coordinates>(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            board.row(i).fill(Figure.X)
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List<Coordinates>(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.O
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.O, status.result)
            Assert.assertEquals(
                List<Coordinates>(size) { index -> Coordinates(index, i) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.X
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List<Coordinates>(size) { index -> Coordinates(index, i) },
                status.coordinates
            )
        }

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List<Coordinates>(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List<Coordinates>(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List<Coordinates>(size) { index -> Coordinates(index, size - 1 - index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List<Coordinates>(size) { index -> Coordinates(index, size - 1 - index) },
            status.coordinates
        )

        board.clear()
        board[0][0] = Figure.O
        board[0][1] = Figure.O
        board[0][2] = Figure.X
        board[1][0] = Figure.X
        board[1][1] = Figure.X
        board[1][2] = Figure.O
        board[2][0] = Figure.O
        board[2][1] = Figure.X
        board[2][2] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.TIE, status.result)
        Assert.assertEquals(emptyList<Coordinates>(), status.coordinates)
    }
}
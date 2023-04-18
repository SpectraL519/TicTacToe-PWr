package com.tictactoe_master

import com.tictactoe_master.logic.Figure
import com.tictactoe_master.logic.GameBoard
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

        for (i in 0 until size) {
            board.clear()
            board.row(i).fill(Figure.O)
            Assert.assertEquals(IWinCondition.Result.O, winCondition.check(board))

            board.clear()
            board.row(i).fill(Figure.X)
            Assert.assertEquals(IWinCondition.Result.X, winCondition.check(board))

            board.clear()
            for (j in 0 until size)
                board[i][j] = Figure.O
            Assert.assertEquals(IWinCondition.Result.O, winCondition.check(board))

            board.clear()
            for (j in 0 until size)
                board[i][j] = Figure.X
            Assert.assertEquals(IWinCondition.Result.X, winCondition.check(board))
        }

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.O
        Assert.assertEquals(IWinCondition.Result.O, winCondition.check(board))

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.X
        Assert.assertEquals(IWinCondition.Result.X, winCondition.check(board))

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.O
        Assert.assertEquals(IWinCondition.Result.O, winCondition.check(board))

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.X
        Assert.assertEquals(IWinCondition.Result.X, winCondition.check(board))

        board.clear()
        Assert.assertEquals(IWinCondition.Result.NONE, winCondition.check(board))

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
        Assert.assertEquals(IWinCondition.Result.TIE, winCondition.check(board))
    }
}
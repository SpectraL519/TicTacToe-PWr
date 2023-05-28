package com.tictactoe_master

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition
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
                List(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            board.row(i).fill(Figure.X)
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.O
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.O, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(index, i) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.X
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(index, i) },
                status.coordinates
            )
        }

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, size - 1 - index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, size - 1 - index) },
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



    @Test
    fun mobiusStripWinConditionTest() {
        val winCondition: IWinCondition = MobiusStripWinCondition

        val size = 5
        val board = GameBoard(size)

        var status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.NONE, status.result)
        Assert.assertEquals(emptyList<Coordinates>(), status.coordinates)

        // Classic wins
        for (i in 0 until size) {
            board.clear()
            board.row(i).fill(Figure.O)
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.O, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            board.row(i).fill(Figure.X)
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(i, index) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.O
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.O, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(index, i) },
                status.coordinates
            )

            board.clear()
            for (j in 0 until size)
                board[j][i] = Figure.X
            status = winCondition.check(board)
            Assert.assertEquals(IWinCondition.Result.X, status.result)
            Assert.assertEquals(
                List(size) { index -> Coordinates(index, i) },
                status.coordinates
            )
        }

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.O
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.O, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, size - 1 - index) },
            status.coordinates
        )

        board.clear()
        for (i in 0 until size)
            board[i][size - 1 - i] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.X, status.result)
        Assert.assertEquals(
            List(size) { index -> Coordinates(index, size - 1 - index) },
            status.coordinates
        )

        board.clear()
        var currentFigure: Figure = Figure.O
        for (i: Int in 0 until size) {
            for (j: Int in 0 until size) {
                board[i][j] = currentFigure
                currentFigure = currentFigure.next()
            }
        }
        board[2][2] = Figure.X
        status = winCondition.check(board)
        Assert.assertEquals(IWinCondition.Result.TIE, status.result)
        Assert.assertEquals(emptyList<Coordinates>(), status.coordinates)

        // Mobius strip wins
        board.clear()
        var coordinates: List<Coordinates>
        for (row: Int in 0 until size) {
            for (lastConsecutive: Int in 0 until size - 1) {
                val coordinatesA = List(lastConsecutive + 1) {
                    index -> Coordinates(row, index)
                }
                val coordinatesB = List(size - 1 - lastConsecutive) {
                    index -> Coordinates(size - 1 - row, size - 1 - index)
                }
                coordinates = (coordinatesA + coordinatesB).sorted()

                board.clear()
                for (column: Int in 0..lastConsecutive)
                    board[row][column] = Figure.O
                for (column: Int in lastConsecutive + 1 until size)
                    board[size - 1 - row][column] = Figure.O

                println("\nboard O:")
                for (i: Int in 0 until size) {
                    for (j: Int in 0 until size) {
                        print(board[i][j].toString() + " ")
                    }
                    println()
                }

                status = winCondition.check(board)
                println("result: " + status.result.toString())
                for (c in status.coordinates)
                    print("$c ")
                println()
                Assert.assertEquals(IWinCondition.Result.O, status.result)
                Assert.assertEquals(coordinates, status.coordinates)

                board.clear()
                for (column: Int in 0..lastConsecutive)
                    board[row][column] = Figure.X
                for (column: Int in lastConsecutive + 1 until size)
                    board[size - 1 - row][column] = Figure.X

                println("\nboard X:")
                for (i: Int in 0 until size) {
                    for (j: Int in 0 until size) {
                        print(board[i][j].toString() + " ")
                    }
                    println()
                }

                status = winCondition.check(board)
                println("result: " + status.result.toString())
                for (c in status.coordinates)
                    print("$c ")
                println()
                Assert.assertEquals(IWinCondition.Result.X, status.result)
                Assert.assertEquals(coordinates, status.coordinates)

                println("\n")
            }
        }
    }
}
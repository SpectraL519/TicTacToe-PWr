package com.tictactoe_master.logic

import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.utils.GameBoard
import com.tictactoe_master.logic.win_condition.IWinCondition
import kotlin.math.min
import kotlin.math.max


class BotHandler
    constructor(
        private val winCondition: IWinCondition,
        private val player: Figure,
        private val depth: Int = 3
    ) {

    fun getMoveCoordinates (board: GameBoard): Coordinates =
        this.minmax(
            board,
            this.depth,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            this.isMaxPlayer(player)
        ).coordinates

    private fun minmax(
        board: GameBoard,
        depth: Int,
        alpha: Long,
        beta: Long,
        maxPlayer: Boolean
    ): MoveParams {
        val result = this.winCondition.check(board).result

        if (
            depth <= 0 ||
            result == IWinCondition.Result.O ||
            result == IWinCondition.Result.X
        ) return MoveParams(Coordinates.NONE, this.winCondition.getEvaluation(board, this.player))

        val player = this.getPlayer(maxPlayer)
        val compare = this.getCompareMethod(maxPlayer)
        var bestMove = MoveParams(Coordinates.NONE, this.getInitEvaluation(maxPlayer))
        var _alpha = alpha
        var _beta = beta


        val boardSize = board.size()
        for (x: Int in 0 until boardSize) {
            for (y: Int in 0 until boardSize) {
                if (board[x][y] == Figure.EMPTY) {
                    board[x][y] = player // make a move
                    val childParams = this.minmax(board, depth - 1, alpha, beta, !maxPlayer)
                    board[x][y] = Figure.EMPTY // undo the move

                    if (compare(childParams.evaluation, bestMove.evaluation)) {
                        bestMove = MoveParams(Coordinates(x, y), childParams.evaluation)
                        if (maxPlayer)
                            _alpha = max(bestMove.evaluation, _alpha)
                        else
                            _beta = min(bestMove.evaluation, _beta)
                        if (_beta <= _alpha)
                            return bestMove
                    }
                }
            }
        }

        return bestMove
    }


    private fun isMaxPlayer (player: Figure): Boolean = (player == Figure.X)

    private fun getPlayer (maxPlayer: Boolean): Figure =
        if (maxPlayer)
            this.player
        else
            this.player.next()

    private fun getCompareMethod (maxPlayer: Boolean): (Long, Long) -> Boolean =
        if (maxPlayer)
            fun (evalA: Long, evalB: Long): Boolean = (evalA > evalB)
        else
            fun (evalA: Long, evalB: Long): Boolean = (evalA < evalB)

    private fun getInitEvaluation (maxPlayer: Boolean): Long =
        if (maxPlayer)
            Long.MIN_VALUE
        else
            Long.MAX_VALUE



    private data class MoveParams
        constructor(
            var coordinates: Coordinates,
            var evaluation: Long
        )
}
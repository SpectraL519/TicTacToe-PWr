package com.tictactoe_master.logic.utils

import com.tictactoe_master.logic.win_condition.IWinCondition



data class Status
    constructor(
        val result: IWinCondition.Result,
        val coordinates: List<Coordinates> = emptyList()
    )

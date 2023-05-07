package com.tictactoe_master.logic.utils



data class Coordinates
    constructor (
        private val _row: Int,
        private val _column: Int
    ) {

    fun row() = this._row
    fun column() = this._column
}

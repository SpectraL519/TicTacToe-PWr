package com.tictactoe_master.logic.utils



data class Coordinates
    constructor (
        val row: Int,
        val column: Int
    ) : Comparable<Coordinates> {

    override fun toString(): String {
        return String.format("C(%d,%d)", this.row, this.column)
    }

    override fun compareTo(other: Coordinates): Int {
        return compareValuesBy(this, other, { it.row }, { it.column })
    }

    companion object {
        val NONE = Coordinates(-1, -1)
        private val REGEX = Regex("C\\((\\d+),(\\d+)\\)")

        fun fromString (strCoordinates: String): Coordinates? {
            val match = REGEX.find(strCoordinates) ?: return null

            val (row, column) = match.destructured
            return Coordinates(row.toInt(), column.toInt())
        }
    }
}

package com.tictactoe_master.logic.utils



enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
    },

    O {
        override fun next(): Figure = X
        override fun toString(): String = "O"
    },

    X {
        override fun next(): Figure = O
        override fun toString(): String = "X"
    };

    abstract fun next(): Figure
}
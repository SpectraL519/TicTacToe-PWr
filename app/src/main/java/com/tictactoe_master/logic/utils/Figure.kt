package com.tictactoe_master.logic.utils



enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
    },

    O {
        override fun next(): Figure = X
        override fun toString(): String = "player O"
    },

    X {
        override fun next(): Figure = O
        override fun toString(): String = "player X"
    };

    abstract fun next(): Figure
}
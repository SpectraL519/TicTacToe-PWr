package com.tictactoe_master.logic.utils

import com.tictactoe_master.R


enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
        override fun getImageResource(): Int{
            return android.R.color.transparent
        }
    },

    O {
        override fun next(): Figure = X
        override fun toString(): String = "O"
        override fun getImageResource(): Int{
            return R.drawable.circle
        }
    },

    X {
        override fun next(): Figure = O
        override fun toString(): String = "X"
        override fun getImageResource(): Int{
            return R.drawable.cross
        }
    };

    abstract fun next(): Figure
    abstract fun getImageResource(): Int
}
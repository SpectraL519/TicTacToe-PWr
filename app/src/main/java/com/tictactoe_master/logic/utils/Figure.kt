package com.tictactoe_master.logic.utils

import com.tictactoe_master.R


enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
        override fun getImageResource(): Int{
            return android.R.color.transparent
        }
        override fun setImageResource(res: Int) {}
    },

    O {
        private var res: Int = R.drawable.circle
        override fun next(): Figure = X
        override fun toString(): String = "O"
        override fun getImageResource() = res
        override fun setImageResource(res: Int) {
            this.res = res
        }
    },

    X {
        private var res: Int = R.drawable.cross
        override fun next(): Figure = O
        override fun toString(): String = "X"
        override fun getImageResource() = res
        override fun setImageResource(res: Int) {
            this.res = res
        }
    };

    abstract fun next(): Figure
    abstract fun getImageResource(): Int
    abstract fun setImageResource(res: Int)
}
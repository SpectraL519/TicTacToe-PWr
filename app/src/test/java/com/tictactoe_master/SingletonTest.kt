package com.tictactoe_master

import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition
import org.junit.Assert
import org.junit.Test


class SingletonTest {
    @Test
    fun classicWinConditionTest() {
        val condition1 = ClassicWinCondition
        val condition2 = ClassicWinCondition

        Assert.assertSame(condition1, condition2)
    }

    @Test
    fun mobiusStripWinConditionTest() {
        val condition1 = MobiusStripWinCondition
        val condition2 = MobiusStripWinCondition

        Assert.assertSame(condition1, condition2)
    }
}
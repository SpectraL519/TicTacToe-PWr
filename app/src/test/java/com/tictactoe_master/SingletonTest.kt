package com.tictactoe_master

import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import org.junit.Assert
import org.junit.Test


class SingletonTest {
    @Test
    fun classicWinConditionTest() {
        val condition1 = ClassicWinCondition
        val condition2 = ClassicWinCondition

        Assert.assertSame(condition1, condition2)
    }

}
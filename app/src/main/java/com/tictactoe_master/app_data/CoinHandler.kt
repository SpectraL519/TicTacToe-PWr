package com.tictactoe_master.app_data

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tictactoe_master.logic.win_condition.IWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

object CoinHandler {
    private var balance: Int = 0

    fun getBalance(): Int {
        return balance
    }

    fun setBalance(b: Int) {
        this.balance = b
    }
    
    fun gameOver(_winCondition: IWinCondition, _boardSize: Int, _points: Int = 1) {
        var winning = _boardSize * _points
        if (_winCondition == MobiusStripWinCondition)
            winning *= 2
        balance += winning
    }

    fun loadBalance(context: AppCompatActivity) {
        try {
            balance = FileDataHandler.readInt(context, "coinBalance")
        }
        catch (e: Exception) {
            balance = 0
            Log.e("exception", e.toString())
        }

    }

    fun saveBalance(context: AppCompatActivity) {
        FileDataHandler.writeInt(context, "coinBalance", balance)
    }
}
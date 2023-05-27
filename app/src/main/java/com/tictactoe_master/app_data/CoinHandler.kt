package com.tictactoe_master.app_data

import com.tictactoe_master.logic.win_condition.IWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object CoinHandler {
    private var balance: Int = 0
    private val saveFile: String = "data.txt"
    fun getBalance(): Int {
        return balance
    }
    fun gameOver(_winCondition: IWinCondition, _boardSize: Int) {
        var winning = _boardSize
        if (_winCondition == MobiusStripWinCondition)
            winning *= 2
        balance += winning
    }
    fun gameOver(_winCondition: IWinCondition, _boardSize: Int, _points: Int) {
        var winning = _boardSize * _points
        if (_winCondition == MobiusStripWinCondition)
            winning *= 2
        balance += winning
    }

    fun loadBalance(filesDir: File) {
        var reader = File(filesDir, saveFile)
        if (!reader.isFile || !reader.canRead())
            return
        var stream = FileInputStream(reader)
        balance = stream.read()
    }
    fun saveBalance(filesDir: File) {
        var writer = FileOutputStream(File(filesDir, saveFile))
        writer.write(balance)
        writer.close()
    }
}
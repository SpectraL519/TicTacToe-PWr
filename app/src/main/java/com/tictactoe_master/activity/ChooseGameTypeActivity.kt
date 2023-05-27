package com.tictactoe_master.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.RangeSlider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R
import com.tictactoe_master.app_data.CoinHandler

class ChooseGameTypeActivity : AppCompatActivity() {
    private var chosenBoardSize = 3
    private var chosenWinCond: WinCondition = WinCondition.CLASSIC
    private var chosenGameType: GameType = GameType.CLASSIC
    private var pointsToWin = 2
    private var gameMode = ""

    private lateinit var accountTV: TextView
    private lateinit var decreaseSizeBT: ImageView
    private lateinit var increaseSizeBT: ImageView
    private lateinit var sizeTV: TextView
    private lateinit var chosenWinCondSW: SwitchCompat
    private lateinit var chosenGameTypeSW: SwitchCompat
    private lateinit var pointsToWinLL: LinearLayout
    private lateinit var pointsToWinTV: TextView
    private lateinit var pointsToWinRS: RangeSlider
    private lateinit var startGameBT: Button
    private lateinit var coinsTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_game_type)

        this.gameMode = intent.getStringExtra("game_mode").toString()

        this.initView()
    }

    override fun onStart() {
        super.onStart()

        this.setAccountTVText()
    }

    override fun onResume() {
        super.onResume()
        coinsTV.text = String.format(
            "%s %s",
            CoinHandler.getBalance(),
            getText(R.string.currency)
        )
    }
    override fun onStop() {
        super.onStop()
        CoinHandler.saveBalance(this.filesDir)
    }
    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    private fun initView() {
        this.decreaseSizeBT = findViewById(R.id.decrease_size_iv)
        this.increaseSizeBT = findViewById(R.id.increase_size_iv)
        this.sizeTV = findViewById(R.id.sizeTV)
        this.chosenWinCondSW = findViewById(R.id.chosen_win_cond_sw)
        this.chosenGameTypeSW = findViewById(R.id.chosen_game_type_sw)
        this.pointsToWinLL = findViewById(R.id.points_to_win_ll)
        this.pointsToWinTV = findViewById(R.id.points_to_win_tv)
        this.pointsToWinRS = findViewById(R.id.points_to_win_sb)
        this.startGameBT = findViewById(R.id.start_game_bt)
        this.accountTV = findViewById(R.id.account_tv)
        this.coinsTV = findViewById(R.id.coins_tv)

        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }

        this.sizeTV.text = this.chosenBoardSize.toString()

        this.pointsToWinTV.text = String.format(
            "%s %d",
            getString(R.string.number_of_points_to_win),
            this.pointsToWin
        )
        this.pointsToWinLL.visibility = View.INVISIBLE

        this.decreaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize > MIN_BOARD_SIZE) {
                this.chosenBoardSize--
                this.sizeTV.text = this.chosenBoardSize.toString()
            }
        }

        this.increaseSizeBT.setOnClickListener {
            if (this.chosenBoardSize < MAX_BOARD_SIZE) {
                this.chosenBoardSize++
                this.sizeTV.text = this.chosenBoardSize.toString()
            }
        }

        this.chosenWinCondSW.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                this.chosenWinCond = WinCondition.CLASSIC
                this.chosenWinCondSW.text = getString(R.string.classic_win_condition)
            }
            else {
                this.chosenWinCond = WinCondition.MOBIUS
                this.chosenWinCondSW.text = getString(R.string.mobius_strip_win_condition)
            }
        }

        this.chosenGameTypeSW.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                this.chosenGameType = GameType.CLASSIC
                this.chosenGameTypeSW.text = getString(R.string.classic_game)
                this.pointsToWinLL.visibility = View.INVISIBLE
            }
            else {
                this.chosenGameType = GameType.POINT
                this.chosenGameTypeSW.text = getString(R.string.point_game)
                this.pointsToWinLL.visibility = View.VISIBLE
            }
        }

        this.pointsToWinRS.addOnChangeListener { _, value, _ ->
            this.pointsToWin = value.toInt()
            this.pointsToWinTV.text = String.format(
                "%s %d",
                getString(R.string.number_of_points_to_win),
                this.pointsToWin
            )
        }

        this.startGameBT.setOnClickListener {
            this.startGame()
        }
    }

    private fun startGame() {
        val gameActivity = when (this.gameMode) {
            "1_v_1_online" -> OnlineGameActivity::class.java
            "1_v_bot" -> BotGameActivity::class.java
            else -> GameActivity::class.java
        }

        val gameIntent = Intent(this, gameActivity).apply {
            putExtra("size", this@ChooseGameTypeActivity.chosenBoardSize)
            putExtra("win_cond", this@ChooseGameTypeActivity.chosenWinCond.toString())
            putExtra("game_type", this@ChooseGameTypeActivity.chosenGameType.toString())
            if (this@ChooseGameTypeActivity.chosenGameType == GameType.POINT)
                putExtra("points_to_win", this@ChooseGameTypeActivity.pointsToWin)
        }

        startActivity(gameIntent)
    }

    companion object {
        const val MIN_BOARD_SIZE = 3
        const val MAX_BOARD_SIZE = 9
    }

    private enum class WinCondition {
        CLASSIC { override fun toString(): String = "classic" },
        MOBIUS { override fun toString(): String = "mobius" }
    }

    private enum class GameType {
        CLASSIC { override fun toString(): String = "classic" },
        POINT { override fun toString(): String = "point" }
    }
}
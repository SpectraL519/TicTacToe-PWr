package com.tictactoe_master

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.activity.ChooseGameTypeActivity
import com.tictactoe_master.activity.GalleryActivity
import com.tictactoe_master.activity.LoginActivity
import com.tictactoe_master.app_data.CoinHandler
import com.tictactoe_master.app_data.FileDataHandler

class MainFragment : Fragment() {

    private lateinit var oneVsOneCV: CardView
    private lateinit var oneVsBotCV: CardView
    private lateinit var oneVsOneOnlineCV: CardView
    private lateinit var shopCV: CardView
    private lateinit var accountTV: TextView
    private lateinit var coinsTV: TextView
    private var saveFileLoaded = false

    private lateinit var myView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView =inflater.inflate(R.layout.fragment_main, container, false)

        if (!saveFileLoaded) {
            CoinHandler.loadBalance(activity as AppCompatActivity)
            saveFileLoaded = true
        }

        this.initPrices()
        this.initView()
        // Inflate the layout for this fragment
        return myView
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
        CoinHandler.saveBalance(activity as AppCompatActivity)
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    private fun initView() {
        this.oneVsOneCV = myView.findViewById(R.id.one_v_one_cv)
        this.oneVsBotCV = myView.findViewById(R.id.one_v_bot_cv)
        this.oneVsOneOnlineCV = myView.findViewById(R.id.one_v_one_online_cv)
        this.shopCV = myView.findViewById(R.id.shop_cv)
        this.accountTV = myView.findViewById(R.id.account_tv)
        this.coinsTV = myView.findViewById(R.id.coins_tv)

        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(activity, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(activity, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }

        this.oneVsOneCV.setOnClickListener {
            this.startGame("1_v_1")
        }

        this.oneVsBotCV.setOnClickListener {
            this.startGame("1_v_bot")
        }

        this.oneVsOneOnlineCV.setOnClickListener {
            if (Firebase.auth.currentUser == null)
                Toast.makeText(activity, "First log in to play online", Toast.LENGTH_SHORT).show()
            else
                this.startGame("1_v_1_online")
        }

        this.shopCV.setOnClickListener {
            val myIntent = Intent(activity, GalleryActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun startGame (gameMode: String) {
        val gameIntent = Intent(activity, ChooseGameTypeActivity::class.java).apply {
            putExtra("game_mode", gameMode)
        }
        startActivity(gameIntent)
    }

    private fun initPrices(){
        if(!FileDataHandler.checkInt(activity as AppCompatActivity, "p0")){
            FileDataHandler.writeInt(activity as AppCompatActivity, "img1", R.drawable.circle)
            FileDataHandler.writeInt(activity as AppCompatActivity, "img2", R.drawable.cross)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p0", 0)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p1", 0)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p2", 20)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p3", 20)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p4", 20)
        }
    }

}
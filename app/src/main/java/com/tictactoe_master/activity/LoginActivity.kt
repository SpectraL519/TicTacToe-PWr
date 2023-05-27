package com.tictactoe_master.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBT: Button
    private lateinit var notYetAccountTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.initView()
    }

    private fun initView() {
        this.auth = Firebase.auth

        this.emailET = findViewById(R.id.email_et)
        this.passwordET = findViewById(R.id.password_et)
        this.loginBT = findViewById(R.id.login_bt)
        this.notYetAccountTV = findViewById(R.id.not_yet_account_tv)

        this.loginBT.setOnClickListener {
            val email = this.emailET.text.toString()
            val password = this.passwordET.text.toString()
            if (this.validateData(email, password))
                this.login(email, password)
        }

        this.notYetAccountTV.setOnClickListener {
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }
    }

    private fun validateData(email: String, password: String): Boolean {
        if (email == "" || password == "") {
            Toast.makeText(this, "Enter e-mail and password", Toast.LENGTH_LONG).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter correct e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login succeeded", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
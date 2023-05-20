package com.tictactoe_master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBT: Button

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
        this.loginBT.setOnClickListener {
            val email = this.emailET.text.toString()
            val password = this.passwordET.text.toString()
            if (this.validateData(email, password))
                this.login(email, password)
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

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "You are already logged in", Toast.LENGTH_LONG).show()
            finish()
        }
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
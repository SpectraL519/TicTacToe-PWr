package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var repeatPasswordET: EditText
    private lateinit var signInBT: Button
    private lateinit var alreadyAccountTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        this.initView()
    }

    private fun initView() {
        this.auth = Firebase.auth

        this.emailET = findViewById(R.id.email_et)
        this.passwordET = findViewById(R.id.password_et)
        this.repeatPasswordET = findViewById(R.id.repeat_password_et)
        this.signInBT = findViewById(R.id.sign_in_bt)
        this.alreadyAccountTV = findViewById(R.id.already_account_tv)

        this.signInBT.setOnClickListener {
            val email = this.emailET.text.toString()
            val password = this.passwordET.text.toString()
            val password2 = this.repeatPasswordET.text.toString()
            if (this.validateData(email, password, password2))
                this.createNewUser(email, password)
        }

        this.alreadyAccountTV.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }

    private fun validateData(email: String, password: String, password2: String): Boolean {
        if (email == "" || password == "") {
            Toast.makeText(this, "Enter e-mail and password", Toast.LENGTH_LONG).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter correct e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        if (password != password2) {
            Toast.makeText(this, "Given password are different", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun createNewUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign in succeeded", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "There is already an account with this e-mail address", Toast.LENGTH_LONG).show()
                }
            }
    }
}
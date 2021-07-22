package com.sanjay.foodrunner.files.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sanjay.foodrunner.R

class LoginActivity : AppCompatActivity() {

    lateinit var txtSignUp: TextView
    lateinit var txtForgotPassword: TextView
    lateinit var etPhone: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button

    lateinit var userLogin: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        userLogin =
            getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        var isLoggedIn = userLogin.getBoolean("isLoggedIn", false)
        setContentView(R.layout.login_activity)

        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        title = "Login"
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)


        val loginToMain = Intent(this@LoginActivity, MainActivity::class.java)
        btnLogin = findViewById(R.id.btnlogin)
        txtSignUp = findViewById<TextView>(R.id.txtSignUp)

        val toSignUp = Intent(this@LoginActivity, RegisterActivity::class.java)

        txtSignUp.setOnClickListener {
            startActivity(toSignUp)
        }
        val toForgotScreen = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtForgotPassword.setOnClickListener {
            startActivity(toForgotScreen)

        }


        btnLogin.setOnClickListener {
            val enteredPhoneNumber = etPhone.text.toString()
            val enteredPassword = etPassword.text.toString()

            val mobileNumber = userLogin.getString("Mobile Number", "Mobile Number") as String
            val password = userLogin.getString("Password", "Password") as String

            if (enteredPassword.isEmpty() || enteredPhoneNumber.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Credentials can't be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (enteredPhoneNumber == mobileNumber && password == enteredPassword) {

                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                    savePreferences()
                    startActivity(loginToMain)
                    finish()


                } else {

                    Toast.makeText(this@LoginActivity, "Incorrect Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    fun savePreferences() {
        userLogin.edit().putBoolean("isLoggedIn", true).apply()
    }

}

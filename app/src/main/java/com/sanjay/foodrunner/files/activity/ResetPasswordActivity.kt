package com.sanjay.foodrunner.files.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sanjay.foodrunner.R

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var passwordPreferences:SharedPreferences
    lateinit var etPassword:EditText
    lateinit var etPasswordRepeat:EditText
    lateinit var btnResetPassword:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        passwordPreferences=getSharedPreferences(getString(R.string.preferences_file_name),Context.MODE_PRIVATE)

        etPassword=findViewById(R.id.etPassword)
        etPasswordRepeat=findViewById(R.id.etPasswordRepeat)
        btnResetPassword=findViewById(R.id.btnResetPassword)

        btnResetPassword.setOnClickListener{
            var password=etPassword.text.toString()
            var passwordRepeat=etPasswordRepeat.text.toString()

            if(password==passwordRepeat){
                passwordPreferences.edit().putString("Password", password).apply()

                Toast.makeText(this@ResetPasswordActivity,"Password reset successful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@ResetPasswordActivity,
                    LoginActivity::class.java))

            }else{
                Toast.makeText(this@ResetPasswordActivity,"Password is not matching",Toast.LENGTH_SHORT).show()
            }

        }

    }
}

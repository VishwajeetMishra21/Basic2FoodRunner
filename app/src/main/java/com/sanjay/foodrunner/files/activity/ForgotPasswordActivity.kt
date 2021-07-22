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

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var etPhone:EditText
    lateinit var etEmail:EditText
    lateinit var btnNext:Button
    lateinit var userPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        userPreferences=getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        var registeredPhone=userPreferences.getString("Mobile Number","Mobile Number")
        var registeredEmail=userPreferences.getString("Email","Email")




        title="Forgot Password"

        btnNext=findViewById(R.id.btnNext)
        etPhone=findViewById(R.id.etResetPasswordMobileNumber)
        etEmail=findViewById(R.id.etResetPasswordEmailAddress)


        val toLogin= Intent(this@ForgotPasswordActivity,
            ResetPasswordActivity::class.java)

        btnNext.setOnClickListener{
            var phone=etPhone.text.toString()
            var email=etEmail.text.toString()

            if(registeredPhone==phone&& registeredEmail==email) {
                startActivity(toLogin)
                finish()
            }else{
                Toast.makeText(this@ForgotPasswordActivity,"Phone number not registered",Toast.LENGTH_SHORT).show()
            }
        }
    }


}

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

class RegisterActivity : AppCompatActivity() {

    lateinit var btnRegister: Button
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etDeliveryAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText

    lateinit var userRegistration:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        userRegistration=getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        title = "Register Yourself"

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etDeliveryAddress = findViewById(R.id.etDeliveryAddress)
        etPassword = findViewById(R.id.etPasswordRegister)
        etConfirmPassword = findViewById(R.id.etPasswordRepeat)
        btnRegister = findViewById(R.id.btnRegister)

        val goToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)

        btnRegister.setOnClickListener{
            val name=etName.text.toString()
            val email=etEmail.text.toString()
            val mobileNumber=etMobileNumber.text.toString()
            val deliveryAddress=etDeliveryAddress.text.toString()
            val password=etPassword.text.toString()
            val passwordConfirm=etConfirmPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() || deliveryAddress.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || mobileNumber.isEmpty()){
                Toast.makeText(this@RegisterActivity,"Credentials can't be empty",Toast.LENGTH_SHORT).show()

            }else{
               if(password!=passwordConfirm){
                   Toast.makeText(this@RegisterActivity,"Password is not matching",Toast.LENGTH_SHORT).show()
               }else{
                   registerPreferences(name,email,mobileNumber,deliveryAddress,password)
                   Toast.makeText(this@RegisterActivity,"Registered successfully",Toast.LENGTH_SHORT).show()
                   startActivity(goToLogin)
                   finish()
               }
            }
        }



    }
    fun registerPreferences(name:String,email:String,mobileNumber:String,deliveryAddress:String,password:String){
        userRegistration.edit().putString("Name", name).apply()
        userRegistration.edit().putString("Email", email).apply()
        userRegistration.edit().putString("Mobile Number", mobileNumber).apply()
        userRegistration.edit().putString("Delivery Address", deliveryAddress).apply()
        userRegistration.edit().putString("Password", password).apply()
    }

}

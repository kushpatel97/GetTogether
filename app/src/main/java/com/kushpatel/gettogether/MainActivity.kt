package com.kushpatel.gettogether

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fullName = fullName_register.text.toString()
        val email = email_register.text.toString()
        val password = password_register.text.toString()
        val confPassword = confirm_password_register.text.toString()

        register_button.setOnClickListener {
            Log.d("Main Activity","Email: $email")
            Log.d("Main Activity", "Password: $password")
        }

        login.setOnClickListener {
            val goToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(goToLogin)
        }
    }
}

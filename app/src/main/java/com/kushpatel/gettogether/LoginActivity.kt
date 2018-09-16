package com.kushpatel.gettogether

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {


//    var email:String? = null
//    var password:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        login_button.setOnClickListener {
            loginUser()
//            Log.d("Login Activity", "Email: $email \n Password: $password")
        }

        go_to_register.setOnClickListener {
//            val goToRegister = Intent(this, RegisterActivity::class.java)
//            startActivity(goToRegister)
            finish()
        }
    }


    private fun loginUser(){
        var email = email_login.text.toString()
        var password = password_login.text.toString()

        if(email.isEmpty() or password.isEmpty()){
            Toast.makeText(this, "Cannot leave the email/password fields blank",Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Login Activity", "Logged in User ${it.result.user.uid}")
                    Toast.makeText(this,"Logged in User: ${it.result.user.uid}",Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{
                    Toast.makeText(this,"Unable to login: ${it.message}",Toast.LENGTH_SHORT).show()
                }
    }
}

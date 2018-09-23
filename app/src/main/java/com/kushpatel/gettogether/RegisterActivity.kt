package com.kushpatel.gettogether

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import com.kushpatel.gettogether.models.User
import java.util.*

class RegisterActivity : AppCompatActivity() {


//    var fullName:String? = null
//    var email:String? = null
//    var password:String? = null
//    var confPassword:String? = null
//    var mAuth = FirebaseAuth.getInstance()

    val IMAGE_REQUEST_CODE:Int = 0
    var selectedPhotoUri:Uri? = null
    val REGISTER_ACTIVITY = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        selectPhoto.setOnClickListener{
            Log.d("Main Activity", "Show photo selector")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,IMAGE_REQUEST_CODE)
        }

        register_button.setOnClickListener {
           registerUser()
        }

        login.setOnClickListener {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Log.d("Register Activity", "Choose image")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            selectPhoto.setBackgroundDrawable(bitmapDrawable)
            selectPhoto.text = ""

            Log.d("URI", "============ ${selectedPhotoUri}")
        }



    }


    private fun registerUser(){
        var fullName = fullName_register.text.toString()
        var email = email_register.text.toString()
        var password = password_register.text.toString()
        var username = username_register.text.toString()
        Log.d("Main Activity","Email: $email")
        Log.d("Main Activity", "Password: $password")

        if(email.isBlank() || email.isEmpty()){
            Toast.makeText(this, "Cannot leave the email field empty", Toast.LENGTH_SHORT).show()
            return;
        }
        if(fullName.isBlank() || fullName.isEmpty()){
            Toast.makeText(this, "Cannot leave the Full Name field empty", Toast.LENGTH_SHORT).show()
            return;
        }
        if(password.isBlank() || password.isEmpty()){
            Toast.makeText(this, "Cannot leave the password field empty", Toast.LENGTH_SHORT).show()
            return;
        }
        if(username.isBlank() || username.isEmpty()){
            Toast.makeText(this, "Cannot leave the Username field empty", Toast.LENGTH_SHORT).show()
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener
                    //If successful
                        Log.d("Main Activity", "User created ${it.result.user.uid}")
                        uploadImageToFirebaseStorage()
                    }
                .addOnFailureListener{
                        Log.d("Main Activity", "Failed to create user ${it.message}")
                        Toast.makeText(this, "Failed to create user: ${it.message}",Toast.LENGTH_SHORT).show()
                    }


    }

    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("RegisterActivity","File download link ${it}")
                        saveUserToFirebaseDatabase(it.toString())
                    }
                            .addOnFailureListener{
                                Log.d(REGISTER_ACTIVITY, "========== ${it.message}")
                            }
                }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")


        val username = username_register.text.toString()
        val fullName = fullName_register.text.toString()
        val user = User(uid,username,fullName,profileImageUrl)
        ref.setValue(user)
                .addOnSuccessListener {
                    Log.d(REGISTER_ACTIVITY,"Added User to firebase database")
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Log.d(REGISTER_ACTIVITY, "============== ${it.message}")
                }
    }





}

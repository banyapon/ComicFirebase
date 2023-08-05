package com.daydev.comicfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var regisEmail: EditText
    lateinit var regisPassword: EditText
    lateinit var regisFullname: EditText
    lateinit var regisBio: EditText
    lateinit var regisAddress: EditText
    lateinit var regisPhone: EditText
    lateinit var regisConfirm:EditText
    lateinit var buttonSubmit: Button
    lateinit var email:String
    lateinit var password:String
    lateinit var confirm:String
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        regisEmail = findViewById<EditText>(R.id.regisEmail)
        regisPassword = findViewById<EditText>(R.id.regisPassword)
        regisFullname = findViewById<EditText>(R.id.regisFullname)
        regisBio = findViewById<EditText>(R.id.regisBio)
        regisAddress = findViewById<EditText>(R.id.regisAddress)
        regisPhone = findViewById<EditText>(R.id.regisPhone)
        regisConfirm = findViewById<EditText>(R.id.regisConfirm)
        buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        buttonSubmit!!.setOnClickListener {
            createAccount()
        }
    }

    override fun onStart(){
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            val uid = user.uid
            val email = user.email
            Toast.makeText(
                this@SignupActivity,
                "Welcome: $email your ID is: $uid",
                Toast.LENGTH_SHORT
            ).show()
            val intentSession = Intent(this, MainActivity::class.java)
            startActivity(intentSession)
        }
    }

    private fun createAccount() {
        email = regisEmail.text.toString()
        password = regisPassword.text.toString()
        confirm = regisConfirm.text.toString()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(applicationContext, "Please Enter your Email!", Toast.LENGTH_SHORT).show()
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(applicationContext, "Please Enter your Password!", Toast.LENGTH_SHORT).show()
        }else if(password != confirm){
            Toast.makeText(applicationContext, "Password not Match!", Toast.LENGTH_SHORT).show()
        }else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task -> if(task.isSuccessful){
                    Log.d("MyApp", "Create New User Success!")
                    val user = mAuth.currentUser
                    val databaseReference = database.reference.child("users").push()
                    databaseReference.child("uid").setValue(user!!.uid)
                    databaseReference.child("email").setValue(user.email)
                    databaseReference.child("fullname").setValue(regisFullname.text.toString())
                    databaseReference.child("bio").setValue(regisBio.text.toString())
                    databaseReference.child("phone").setValue(regisPhone.text.toString())
                    databaseReference.child("address").setValue(regisAddress.text.toString())
                    updateUI(user)

                } else{
                    Log.w("MyApp", "Failure Process!", task.exception)
                    Toast.makeText(this@SignupActivity, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        }
    }
}
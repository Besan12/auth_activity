package com.example.auth_homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        var email = signUpEmail.text
        var password = signUpPassword.text
        var confirmePassword = signUpConfirmePassword.text

        signup_btn.setOnClickListener{
            if(email.toString().isEmpty() || password.toString().isEmpty() || confirmePassword.toString().isEmpty()){
                Toast.makeText(this, "you must fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!confirmePassword.toString().equals(password.toString())){
                Toast.makeText(this, "Not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            createAccount(email.toString(), password.toString())
        }

        login_btn2.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }
    }
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("createAccount", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w("createAccount", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed!", Toast.LENGTH_SHORT).show()

                }
            }
    }
    fun updateUI(user: FirebaseUser?) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", user!!.email)
        intent.putExtra("id", user.uid)
        startActivity(intent)
    }
}
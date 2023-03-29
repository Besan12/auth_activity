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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class Login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        var email = loginEmail.text
        var password = loginPassword.text

        login_btn.setOnClickListener{
            if(email.toString().isEmpty() || password.toString().isEmpty()){
                Toast.makeText(this, "you must fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login(email.toString(), password.toString())
        }

        signup_btn2.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
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
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("login", "LogInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w("login", "LogInWithEmail:failure", task.exception)
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

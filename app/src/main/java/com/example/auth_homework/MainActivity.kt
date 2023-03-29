package com.example.auth_homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailtxt.text =  intent.getStringExtra("email")
        idtxt.text =  intent.getStringExtra("id")
        signOut_btn.setOnClickListener {
            //log out code
            Firebase.auth.signOut()
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}
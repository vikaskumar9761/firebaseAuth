package com.example.googleauth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.googleauth.databinding.ActivityDessboardBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Dessboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding:ActivityDessboardBinding by lazy {
        ActivityDessboardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        binding.logOut.setOnClickListener {
       //     auth.signOut()
            val gos= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("key")
                .requestEmail().build()

            GoogleSignIn.getClient(this,gos).signOut()
            startActivity(Intent(this,loginActivity::class.java))
            finish()
        }


    }
}
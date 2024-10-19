package com.example.googleauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.googleauth.databinding.ActivityLoginBinding
import com.example.googleauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding:ActivityLoginBinding by lazy { 
        
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      
        setContentView(binding.root)
        binding.signUp.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))

        }
        
        auth=FirebaseAuth.getInstance()


        
        binding.login1.setOnClickListener {
            val email=binding.email1.text.toString()
            val password=binding.password1.text.toString()
            
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Enter the Email and password", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { 
                    task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "success full login :=${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,Dessboard::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, "login Failed :${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }





    }
}
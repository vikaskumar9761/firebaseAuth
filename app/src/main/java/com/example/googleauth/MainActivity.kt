package com.example.googleauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.googleauth.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.googleauth.loginActivity as loginActivity1

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.login.setOnClickListener { 
            startActivity(Intent(this,loginActivity1::class.java))
            
        }

        binding.crateAccount.setOnClickListener {

            val name=binding.name.text.toString()
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            val re_password=binding.rePassword.text.toString()

            if(name.isEmpty()||email.isEmpty()||password.isEmpty()||re_password.isEmpty()){
                Toast.makeText(this, "fill the all information", Toast.LENGTH_SHORT).show()
            }
            else if (password!=re_password){
                Toast.makeText(this, "password is wrong", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registration is sucsessful", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this,Dessboard::class.java))
                                finish()

                        }
                        else{
                            Toast.makeText(this, " Registration failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

        //google authantication from the firebase
        val gos=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("226660578249-k7e5u95na60u93139skjtesgnvj99i1g.apps.googleusercontent.com")
            .requestEmail().build()

        googleSignInClient= GoogleSignIn.getClient(this,gos)
        binding.google.setOnClickListener {

            val signInClient=googleSignInClient.signInIntent
           launcher.launch(signInClient)
        }

    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result->
        if(result.resultCode== Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account:GoogleSignInAccount?=task.result
                val credential=GoogleAuthProvider.getCredential(account?.idToken,null)

                auth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Successfulū", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,Dessboard::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "Faild", Toast.LENGTH_SHORT).show()
        }
    }
}
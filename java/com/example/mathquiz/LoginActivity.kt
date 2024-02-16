package com.example.mathquiz

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.window.Dialog
import com.example.mathquiz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth=FirebaseAuth.getInstance()
        
        binding.loginPageLoginbtn.setOnClickListener {
            var email=binding.editTextTextEmailAddress.text.toString()
            var pass=binding.editTextTextPassword.text.toString()

            
            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Enter all fields...", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                    if(it.isSuccessful){
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "something went worng: "+it.exception, Toast.LENGTH_SHORT).show()
                    }
                }
                
            }
            
            
        }

        binding.loginPageSingupbtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}
package com.example.mathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mathquiz.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  auth:FirebaseAuth
    private lateinit var  database:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()



        var currUser = auth.currentUser
        if(currUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        binding.signupPageSingupbtn.setOnClickListener{
            var name=binding.editTextText.text.toString()
            var email=binding.editTextTextEmailAddress.text.toString()

            var pass=binding.editTextTextPassword.text.toString()
            var conPass = binding.editTextTextPassword2.text.toString()
           var points:Long=0
            var User=User(name,email,points)
            if(name.isEmpty() || email.isEmpty() || pass.isEmpty() || conPass.isEmpty()){
                Toast.makeText(this, "Enter all feilds", Toast.LENGTH_SHORT).show()
            }else if(pass != conPass){
                Toast.makeText(this, "Both passwords are not same....", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                    if(it.isSuccessful){
                        Toast.makeText(this, "User has successfully created", Toast.LENGTH_SHORT).show()


                        //storing users data....
                        var uid= it.getResult().user?.uid

                        if (uid != null) {
                            database.collection("users").document(uid).set(User).addOnCompleteListener(this){
                                if(it.isSuccessful){
                                    Toast.makeText(this, "created successfully", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this, "Error: "+it.exception, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Something went wrong . Error is: "+ it.exception, Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }

        binding.signupPageLoginbtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
}
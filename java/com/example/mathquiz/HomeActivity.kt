package com.example.mathquiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathquiz.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseFirestore
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()


        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserID != null) {
            // Assuming your Firestore collection is named "users"
            val userDocRef = database.collection("users").document(currentUserID)

            // Fetch user data
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val userName = document.getString("name")
                        val points = document.get("points")
                        // Update UI with username
                        binding.nameview.text=userName
                        binding.pointsv.text= points.toString()
                    } else {
                        binding.nameview.text="WRONG NAME"
                    }
                }.addOnFailureListener(this){
                    binding.nameview.text="GDSC MBU"
                }




        }

        //to show the last score of the user
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val currscore:Long = sharedPreferences.getLong("CurrentScore",0)

        binding.currScore.text=currscore.toString()








        binding.sportscardview.setOnClickListener {
            var catid:String="sportsquestion"
            var questionintent=Intent(this,questionsActivity::class.java)
                questionintent.putExtra("catid",catid)
            startActivity(questionintent)

        }

        //binding.currScore.setText(intent.getIntExtra("score",0))

        binding.MathcardView.setOnClickListener {
            var questionintent=Intent(this,questionsActivity::class.java)
           var catid:String="questionset"
            questionintent.putExtra("catid",catid)
            startActivity(questionintent)
        }





    }
}
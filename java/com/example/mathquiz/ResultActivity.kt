package com.example.mathquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathquiz.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.time.times

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var database:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





        var score:Long=intent.getLongExtra("score",0)

        var points:Long=score *10

        binding.scoreview.text= score.toString()

        binding.pointsview.text=points.toString()



        // Save the score in SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("CurrentScore",score)
        editor.apply()

        database= FirebaseFirestore.getInstance()
        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserID != null) {
            database.collection("users").document(currentUserID).update("points",FieldValue.increment(points))
        }


        binding.restartbtn.setOnClickListener {
            var intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
package com.example.mathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mathquiz.databinding.ActivityQuestionsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class questionsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityQuestionsBinding
    private lateinit var list:ArrayList<Questionset>
    var index=0
    var score:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityQuestionsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list= ArrayList<Questionset>()

        var catid: String? =intent.getStringExtra("catid")

        Toast.makeText(this, "catid:"+catid, Toast.LENGTH_SHORT).show()

        binding.testing.text=catid



        if (catid != null) {
            Firebase.firestore.collection(catid).get().addOnSuccessListener { doct->
                list.clear()
                for (i in doct.documents){
                    var questionset=i.toObject(Questionset::class.java)
                    list.add(questionset!!)
                }

                if(list.isNotEmpty()){
                    binding.question.setText(list.get(0).question)
                    binding.option1.setText(list.get(0).option1)
                    binding.option2.setText(list.get(0).option2)
                    binding.option3.setText(list.get(0).option3)
                    binding.option4.setText(list.get(0).option4)
                }


            }
        }



//        list.add(Questionset("2+2","2","3","4","5","4"))
//        list.add(Questionset("2+2","2","3","4","5","4"))
//        list.add(Questionset("2+3","1","3","4","5","5"))
//        list.add(Questionset("2+2","2","3","4","5","4"))
//        list.add(Questionset("2+4","6","3","4","5","6"))




        binding.option1.setOnClickListener {
            next(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener {
            next(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            next(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener {
            next(binding.option4.text.toString())
        }







    }

    private fun next(i: String) {
            if(list.get(index).ans.equals(i)){
                score++
            }
            index++
        if(index>=list.size){
            Toast.makeText(this, "score is: "+score, Toast.LENGTH_SHORT).show()
            val intent= Intent(this,ResultActivity::class.java)
            intent.putExtra("score",score)
            startActivity(intent)
            finish()
        }else {
            binding.question.setText(list.get(index).question)
            binding.option1.setText(list.get(index).option1)
            binding.option2.setText(list.get(index).option2)
            binding.option3.setText(list.get(index).option3)
            binding.option4.setText(list.get(index).option4)
        }
    }
}
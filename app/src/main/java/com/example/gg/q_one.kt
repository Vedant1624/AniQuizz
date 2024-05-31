package com.example.gg

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gg.databinding.ActivityQoneBinding
import com.example.gg.databinding.ActivityScoreDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class q_one : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQoneBinding

    var currentQuestionIndex = 0;
    var selectedAnswer = ""
    var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Option1.setOnClickListener(this@q_one)
            Option2.setOnClickListener(this@q_one)
            Option3.setOnClickListener(this@q_one)
            Option4.setOnClickListener(this@q_one)
            btnNext.setOnClickListener(this@q_one)
        }

        loadQuestions()
        startTimer()

        val btnimg: ImageButton = findViewById(R.id.btnback)

        btnimg.setOnClickListener {
            val intent = Intent(this , Home::class.java)
            startActivity(intent)
        }
    }

    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }

            override fun onFinish() {
                finish()
            }

        }.start()
    }

    private fun loadQuestions(){
        selectedAnswer = ""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size} "
            questionProgressIndicator.progress =
                ( currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            Option1.text = questionModelList[currentQuestionIndex].options[0]
            Option2.text = questionModelList[currentQuestionIndex].options[1]
            Option3.text = questionModelList[currentQuestionIndex].options[2]
            Option4.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            Option1.setBackgroundResource(R.drawable.rectangle_bg_white_a700_border_gray_300_radius_10)
            Option2.setBackgroundResource(R.drawable.rectangle_bg_white_a700_border_gray_300_radius_10)
            Option3.setBackgroundResource(R.drawable.rectangle_bg_white_a700_border_gray_300_radius_10)
            Option4.setBackgroundResource(R.drawable.rectangle_bg_white_a700_border_gray_300_radius_10)
        }

        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.btnNext){
            //next button is clicked
            if(selectedAnswer.isEmpty()){
                Toast.makeText(applicationContext,"Please select answer to continue", Toast.LENGTH_SHORT).show()
                return;
            }
            if(selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score of quiz",score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }else{
            //options button is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundResource(R.drawable.rectangle_bg_green_50_border_green_400_radius_10)
        }
    }

    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat() ) *100 ).toInt()

        val dialogBinding  = ActivityScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if(percentage>40){
                scoreTitle.text = "Congrats!"
                scoreTitle.setTextColor(Color.BLACK)
            }else{
                scoreTitle.text = "Oops! "
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions "
            finishBtn.setOnClickListener {
               // saveScoreToFirebase(score)
                updateScoreToFirebase(score)
                finish()
            }
        }
        if (!isFinishing) {
            AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setCancelable(false)
                .show()
        }
    }



    private fun updateScoreToFirebase(score: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        userId?.let { uid ->
            usersRef.child(uid).child("score").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentScore = dataSnapshot.getValue(Int::class.java)

                    // Calculate the updated score
                    val updatedScore = currentScore?.plus(score) ?: score

                    // Update the score in the database
                    val scoreUpdateMap = HashMap<String, Any>()
                    scoreUpdateMap["score"] = updatedScore
                    usersRef.child(uid).updateChildren(scoreUpdateMap)
                        .addOnSuccessListener {
                            Log.d(TAG, "Score updated successfully.")
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Error updating score: ${exception.message}", exception)
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException())
                }
            })
        }
    }


}
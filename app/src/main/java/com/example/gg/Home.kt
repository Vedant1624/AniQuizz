package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.gg.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class Home : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var quizModelList : MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    private lateinit var db: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var txtUsername: TextView
    private lateinit var txtRankCounter: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        db = FirebaseDatabase.getInstance().reference
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
        txtUsername = findViewById(R.id.txtUsername)
        txtRankCounter = findViewById(R.id.txtRankCounter)

        retrieveUsername()
        retrieveScore()

        val btnTakeQuizz : AppCompatButton = findViewById(R.id.btnTakeAQuiz)

        btnTakeQuizz.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
//            getDataQuizz(it)
        }

        val btnBeg: LinearLayout = findViewById(R.id.btnBeginners)

        btnBeg.setOnClickListener {
            getDataBeginners(it)
        }

        val btnMid: LinearLayout = findViewById(R.id.btnMedium)

        btnMid.setOnClickListener {
            getDataMedium(it)
        }

        val btnAdv: LinearLayout = findViewById(R.id.btnAdvance)

        btnAdv.setOnClickListener {
            getDataAdvance(it)
        }

        val btnimgProfile: ImageView = findViewById(R.id.imgProfile)

        btnimgProfile.setOnClickListener {
            val intent = Intent(this , profile::class.java)
            startActivity(intent)
            Animatoo.animateFade(this)
        }

        val frameLayout = findViewById<FrameLayout>(R.id.frame)
        frameLayout.setOnClickListener {

        }

        val mainProfileImageView: ImageView = findViewById(R.id.imgProfile)

        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val userId = user.uid
                val profileImageUrlRef = db.child("users").child(userId).child("profileImageUrl")

                profileImageUrlRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val profileImageUrl = snapshot.value.toString()
                        Glide.with(this@Home)
                            .load(profileImageUrl)
                            .into(mainProfileImageView)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            } else {
                // User is signed out
            }
        }


//        val imageSlider :ImageSlider = findViewById(R.id.imageSlider)
//        val imagelist = ArrayList<SlideModel>()
//
//        imagelist.add(SlideModel(R.drawable.img_rectangle24))
//        imagelist.add(SlideModel(R.drawable.img_rectangle25))
//        imagelist.add(SlideModel(R.drawable.img_rectangle26))
//        imagelist.add(SlideModel(R.drawable.img_rectangle23))
//
//        imageSlider.setImageList(imagelist,ScaleTypes.FIT)

    }

    private fun setupRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

//    private fun getDataQuizz(view: View) {
//        FirebaseDatabase.getInstance().reference
//            .child("quizz")
//            .child("1")
//            .get()
//            .addOnSuccessListener { dataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    val model = dataSnapshot.getValue(QuizModel::class.java)
//                    if (model != null) {
//                        navigateToQuestionActivity(view, model.questionList, model.time)
//                    } else {
//
//                    }
//                } else {
//
//                }
//            }
//            .addOnFailureListener { exception ->
//
//            }
//    }

    private fun getDataBeginners(view: View) {
        FirebaseDatabase.getInstance().reference
            .child("quizz")
            .child("2")
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val model = dataSnapshot.getValue(QuizModel::class.java)
                    if (model != null) {
                        navigateToQuestionActivity(view, model.questionList, model.time)
                    } else {

                    }
                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun getDataMedium(view: View) {
        FirebaseDatabase.getInstance().reference
            .child("quizz")
            .child("3")
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val model = dataSnapshot.getValue(QuizModel::class.java)
                    if (model != null) {
                        navigateToQuestionActivity(view, model.questionList, model.time)
                    } else {

                    }
                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun getDataAdvance(view: View) {
        FirebaseDatabase.getInstance().reference
            .child("quizz")
            .child("4")
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val model = dataSnapshot.getValue(QuizModel::class.java)
                    if (model != null) {
                        navigateToQuestionActivity(view, model.questionList, model.time)
                    } else {

                    }
                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun navigateToQuestionActivity(view: View, questionList: List<QuestionModel>, time: String) {
        val intent = Intent(view.context, q_one::class.java)
        q_one.questionModelList = questionList
        q_one.time = time
        view.context.startActivity(intent)
    }


    private fun retrieveUsername() {
        val userId = firebaseAuth.currentUser?.uid ?: ""

        val usersReference = database.reference.child("users").child(userId)

        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val username = snapshot.child("username").getValue(String::class.java)
                    txtUsername.text = "$username"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Home, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun retrieveScore() {
        val userId = firebaseAuth.currentUser?.uid ?: ""

        val usersReference = database.reference.child("users").child(userId)

        usersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val score = snapshot.child("score").getValue(Int::class.java)
                    txtRankCounter.text = "Score $score"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Home, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataFromFirebase(){
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .child("quizz")
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children ){
                        if(snapshot.key != "users"){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                        }
                    }
                }
                setupRecyclerView()
            }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
    }


    fun searchList(text: String) {
        val searchList = java.util.ArrayList<QuizModel>()
        for (dataClass in quizModelList) {
            if (dataClass.title?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}
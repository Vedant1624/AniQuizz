package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.bumptech.glide.request.RequestOptions


class profile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var etEmail: TextView
    private lateinit var etUsername: TextView
    private lateinit var database1: DatabaseReference
    private lateinit var firstPlaceTextView: TextView
    private lateinit var secondPlaceTextView: TextView
    private lateinit var thirdPlaceTextView: TextView
    private lateinit var firstPlaceScoreTextView: TextView
    private lateinit var secondPlaceScoreTextView: TextView
    private lateinit var thirdPlaceScoreTextView: TextView
    private lateinit var imgFirst : ImageView
    private lateinit var imgSecond : ImageView
    private lateinit var imgThird : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        etEmail = findViewById(R.id.edEmail)
        etUsername = findViewById(R.id.edUser)

        database1 = FirebaseDatabase.getInstance().reference.child("users")

        firstPlaceTextView = findViewById(R.id.firstPlaceTextView)
        firstPlaceScoreTextView = findViewById(R.id.firstPlaceScoreTextView)
        imgFirst = findViewById(R.id.imgfirst)
        secondPlaceTextView = findViewById(R.id.secondPlaceTextView)
        secondPlaceScoreTextView = findViewById(R.id.secondPlaceScoreTextView)
        imgSecond = findViewById(R.id.imgSecond)
        thirdPlaceTextView = findViewById(R.id.thirdPlaceTextView)
        thirdPlaceScoreTextView = findViewById(R.id.thirdPlaceScoreTextView)
        imgThird = findViewById(R.id.imgThird)


        retrieveEmailAndUsername()

        val btnedprofile: LinearLayout = findViewById(R.id.edProfile)

        btnedprofile.setOnClickListener {
            val intent = Intent(this , edit_profile::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)
        }

        val btnedrules: LinearLayout = findViewById(R.id.edRules)

        btnedrules.setOnClickListener {
            val intent = Intent(this , TermsofPLay::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)
        }

        val btlogout: LinearLayout = findViewById(R.id.btnLogOut)

        btlogout.setOnClickListener {
            val intent = Intent(this , login::class.java)
            startActivity(intent)
            Animatoo.animateSlideRight(this)
        }

        val btnimg: ImageButton = findViewById(R.id.btnback)

        btnimg.setOnClickListener {
            val intent = Intent(this , Home::class.java)
            startActivity(intent)
            Animatoo.animateSlideRight(this)
        }


        val mainProfileImageView: ImageView = findViewById(R.id.imageProfile)
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val userId = user.uid
                val profileImageUrlRef = database1.child(userId).child("profileImageUrl")

                profileImageUrlRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val profileImageUrl = snapshot.value.toString()
                        Glide.with(this@profile)
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
    }

    private fun retrieveEmailAndUsername() {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        val userEmail = firebaseAuth.currentUser?.email
        val usersReference = database.reference.child("users").child(userId)

        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val username = snapshot.child("username").getValue(String::class.java)
                    etEmail.setText(userEmail)
                    etUsername.setText(username)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@profile, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })

        val query = database1.orderByChild("score").limitToLast(3)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val topUsers = mutableListOf<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { topUsers.add(it) }
                }

                if (topUsers.size >= 3) {
                    firstPlaceTextView.text = topUsers[2].username
                    firstPlaceScoreTextView.text = "${topUsers[2].score} Pts"
                    secondPlaceTextView.text = topUsers[1].username
                    secondPlaceScoreTextView.text = "${topUsers[1].score} Pts"
                    thirdPlaceTextView.text = topUsers[0].username
                    thirdPlaceScoreTextView.text = "${topUsers[0].score} pts"

                    loadImage(topUsers[2].profileImageUrl, imgFirst)
                    loadImage(topUsers[1].profileImageUrl, imgSecond)
                    loadImage(topUsers[0].profileImageUrl, imgThird)
                } else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })



    }
    private fun loadImage(imageUrl: String?, targetImageView: ImageView) {
        imageUrl?.let { url ->
            Glide.with(this)
                .load(url)
                .apply(RequestOptions().placeholder(R.drawable.image_not_found)) // Placeholder image
                .into(targetImageView)
        }
    }


}
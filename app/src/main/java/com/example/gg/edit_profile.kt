package com.example.gg

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class edit_profile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var etEmail: TextView
    private lateinit var etUsername: TextView
    private lateinit var edNewPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        db = FirebaseDatabase.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        etEmail = findViewById(R.id.edEmail)
        etUsername = findViewById(R.id.edUserName)
        edNewPassword = findViewById(R.id.edOldPassword)
        edConfirmPassword = findViewById(R.id.edNewPassword)

        retrieveEmailAndUsername()
        changePassword()

        val btnimg: ImageButton = findViewById(R.id.btnback)

        btnimg.setOnClickListener {
            val intent = Intent(this , profile::class.java)
            startActivity(intent)
            Animatoo.animateSlideRight(this)
        }

        val mainProfileImageView: ImageView = findViewById(R.id.imgMainAvatar)

        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val userId = user.uid
                val profileImageUrlRef = db.child("users").child(userId).child("profileImageUrl")

                profileImageUrlRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val profileImageUrl = snapshot.value.toString()
                        Glide.with(this@edit_profile)
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



        val profileView1: ImageView = findViewById(R.id.imgAvatar1)
        val profileView2: ImageView = findViewById(R.id.imgAvatar2)
        val profileView3: ImageView = findViewById(R.id.imgAvatar3)
        val profileView4: ImageView = findViewById(R.id.imgAvatar4)
        val profileView5: ImageView = findViewById(R.id.imgAvatar5)
        val profileView6: ImageView = findViewById(R.id.imgAvatar6)
        val profileView7: ImageView = findViewById(R.id.imgAvatar7)
        val profileView8: ImageView = findViewById(R.id.imgAvatar8)
        val profileView9: ImageView = findViewById(R.id.imgAvatar9)


       // Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle10.png?alt=media&token=250f1082-0c14-4233-a528-cb82640bcad9").into(mainProfileImageView)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle13.png?alt=media&token=fbf4770b-b864-4835-9858-b5d5eb898cab").into(profileView1)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle10.png?alt=media&token=250f1082-0c14-4233-a528-cb82640bcad9").into(profileView2)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle17.png?alt=media&token=5d34aef6-3826-4d55-8535-bfe0d19f20eb").into(profileView3)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle16.png?alt=media&token=7176bfd8-1c3a-4ea9-a8cf-e15ea7a62392").into(profileView4)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle18.png?alt=media&token=a880577d-2c58-4e77-a6d7-1b57b25f0103").into(profileView5)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle19.png?alt=media&token=336f8626-db34-4619-ae53-98a3febbd1bd").into(profileView6)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle21_60x60.png?alt=media&token=d9ffd8d9-bfeb-4722-b8e3-07db8ac637d9").into(profileView7)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle20.png?alt=media&token=6b84ebc4-22af-4494-ae79-2f4aecf0490d").into(profileView8)
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle19_60x60.png?alt=media&token=7875732f-3f8a-4729-b219-c986b2dd15d3").into(profileView9)


        val profileViews = mutableListOf<ImageView>()
        profileViews.add(profileView1)
        profileViews.add(profileView2)
        profileViews.add(profileView3)
        profileViews.add(profileView4)
        profileViews.add(profileView5)
        profileViews.add(profileView6)
        profileViews.add(profileView7)
        profileViews.add(profileView8)
        profileViews.add(profileView9)

        profileViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val imageUrl = when(imageView) {
                    profileView1 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle13.png?alt=media&token=fbf4770b-b864-4835-9858-b5d5eb898cab"
                    profileView2 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle10.png?alt=media&token=250f1082-0c14-4233-a528-cb82640bcad9"
                    profileView3 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle17.png?alt=media&token=5d34aef6-3826-4d55-8535-bfe0d19f20eb"
                    profileView4 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle16.png?alt=media&token=7176bfd8-1c3a-4ea9-a8cf-e15ea7a62392"
                    profileView5 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle18.png?alt=media&token=a880577d-2c58-4e77-a6d7-1b57b25f0103"
                    profileView6 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle19.png?alt=media&token=336f8626-db34-4619-ae53-98a3febbd1bd"
                    profileView7 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle21_60x60.png?alt=media&token=d9ffd8d9-bfeb-4722-b8e3-07db8ac637d9"
                    profileView8 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle20.png?alt=media&token=6b84ebc4-22af-4494-ae79-2f4aecf0490d"
                    profileView9 -> "https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle19_60x60.png?alt=media&token=7875732f-3f8a-4729-b219-c986b2dd15d3"
                    else -> "" // Default case, if needed
                }

                mainProfileImageView.setImageDrawable(imageView.drawable)

                userId?.let { uid ->
                    FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .child("profileImageUrl").setValue(imageUrl)
                        .addOnSuccessListener {
                            // Handle success if needed
                        }
                        .addOnFailureListener { e ->
                            // Handle failure
                            Log.e(TAG, "Error updating profile image URL: ${e.message}")
                        }
                }
            }
        }

    }

    private fun changePassword() {
        val btnChangePassword: Button = findViewById(R.id.btnSave)
        btnChangePassword.setOnClickListener {
            val newPassword = edNewPassword.text.toString().trim()
            val confirmPassword = edConfirmPassword.text.toString().trim()

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = firebaseAuth.currentUser
            user?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
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
                Toast.makeText(this@edit_profile, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
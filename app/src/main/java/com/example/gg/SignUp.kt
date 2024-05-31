package com.example.gg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent
import android.transition.TransitionInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.gg.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.enterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.exitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.btnSignUp.setOnClickListener{

           // val intent = Intent(this, login::class.java)
            val sharedImageView: ImageView = findViewById(R.id.imageView4)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImageView, "transitionName")
            //startActivity(intent, options.toBundle())


            val username = binding.txtName.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                        if (it.isSuccessful){
                            saveUsernameToDatabase(username)
                            val intent = Intent(this,login::class.java)
                            startActivity(intent, options.toBundle())
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Password Does Not Matched",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            }
        }

        val btnArrowLeft: ImageButton = findViewById(R.id.btnArrowleft)
        btnArrowLeft.setOnClickListener {
            val intent = Intent(this, login::class.java)
            val sharedImageView: ImageView = findViewById(R.id.imageView4)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImageView, "transitionName")
            startActivity(intent, options.toBundle())

        }
    }
    private fun saveUsernameToDatabase(username: String) {

        val usersReference = database.reference.child("users")

        val userId = firebaseAuth.currentUser?.uid ?: ""

        usersReference.child(userId).child("username").setValue(username)
        usersReference.child(userId).child("score").setValue(0)
        usersReference.child(userId).child("profileImageUrl").setValue("https://firebasestorage.googleapis.com/v0/b/aniqz-5c8b0.appspot.com/o/pfp%2Fimg_rectangle10.png?alt=media&token=250f1082-0c14-4233-a528-cb82640bcad9")
    }
}
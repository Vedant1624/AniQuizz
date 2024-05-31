package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.os.Bundle
import android.widget.LinearLayout
import com.blogspot.atifsoftwares.animatoolib.Animatoo

class TermsofPLay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termsof_play)

        val btnIUnderstand: AppCompatButton = findViewById(R.id.btnIUnderstand)

        btnIUnderstand.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
            Animatoo.animateFade(this)
        }
    }
}
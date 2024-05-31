package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.gg.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuote()

        binding.nextBtn.setOnClickListener {
            getQuote()
        }

        val btnimg: ImageView = findViewById(R.id.imageBack)

        btnimg.setOnClickListener {
            val intent = Intent(this , Home::class.java)
            startActivity(intent)
            Animatoo.animateFade(this)
        }

    }

    private fun getQuote(){
        setInProgress(true)

        GlobalScope.launch {
            try{
                val response = RetrofitInstance.quoteApi.getRandomQuote()
                runOnUiThread {
                    setInProgress(false)
                    if (response.isSuccessful) {
                        val quote = response.body()
                        if (quote != null) {
                            setUI(quote)
                        } else {
                            // Handle null response
                            Toast.makeText(applicationContext, "Empty response body", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle unsuccessful response
                        Toast.makeText(applicationContext, "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e : Exception){
                runOnUiThread {
                    setInProgress(false)
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error: ${e.message}", e)
                }
            }
        }
    }


    private fun setUI(quote : QuoteModel){
        binding.quoteTv.text = quote.quote
        binding.characterTv.text = quote.character
        binding.animeTv.text = quote.anime
    }

    private fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.progressBar.visibility = View.VISIBLE
            binding.nextBtn.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        }
    }
}


















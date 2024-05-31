package com.example.gg

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.databinding.QuizItemRecyclerRowBinding
import com.squareup.picasso.Picasso

class DifficultAdapter (private var quizModelList : List<QuizModel>) :
    RecyclerView.Adapter<DifficultAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: QuizItemRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model : QuizModel){
            binding.apply {
                quizTitleText.text = model.title

                Picasso.get().load(model.imagePath).into(quizImageView)

                root.setOnClickListener {
                    val intent  = Intent(root.context,q_one::class.java)
                    q_one.questionModelList = model.questionList
                    q_one.time = model.time

                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuizItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModelList[position])
    }

    fun searchDataList(searchList: List<QuizModel>) {
        quizModelList = searchList
        notifyDataSetChanged()
    }
}
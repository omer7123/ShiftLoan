package com.example.finalproject.ui.boardFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.PagerBoardBinding

class BoardAdapter(
    private val titles: Array<String>,
    private val images: Array<Int>,
    private val firstDescribe: Array<String>,
    private val secondDescribe: Array<String>
) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PagerBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(private val binding: PagerBoardBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun onBind(position: Int) {
            binding.titleTv.text = titles[position]
            binding.illustrationIv.setImageResource(images[position])
            binding.firstDescribeTv.text = firstDescribe[position]
            binding.secondDescribeTv.text = secondDescribe[position]
        }
    }
}
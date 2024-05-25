package com.bangkit.kukuliner.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.FoodItemBinding
import com.bangkit.kukuliner.ui.Kuliner
import com.bumptech.glide.Glide

class MainAdapter(private val kulinerList: List<Kuliner>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val kuliner = kulinerList[position]
        holder.binding.foodName.text = kuliner.name
        holder.binding.foodPrice.text = kuliner.estimatePrice
        Glide.with(holder.itemView.context)
            .load(kuliner.photoUrl)
            .into(holder.binding.foodImage)
    }

class MainViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root)

override fun getItemCount(): Int {
    return kulinerList.size
}
}
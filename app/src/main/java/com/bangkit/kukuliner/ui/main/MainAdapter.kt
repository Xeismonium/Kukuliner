package com.bangkit.kukuliner.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.kukuliner.databinding.FoodItemBinding
import com.bangkit.kukuliner.data.Kuliner
import com.bangkit.kukuliner.ui.detail.DetailActivity
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

        holder.itemView.setOnClickListener {
            val intentDetailFood =
                Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetailFood.putExtra(NAME_FOOD, kuliner.name)
            intentDetailFood.putExtra(DESC_FOOD, kuliner.description)
            intentDetailFood.putExtra(PHOTO_FOOD, kuliner.photoUrl)
            intentDetailFood.putExtra(PRICE_FOOD, kuliner.estimatePrice)
            intentDetailFood.putExtra(LAT_FOOD, kuliner.lat)
            intentDetailFood.putExtra(LON_FOOD, kuliner.lon)
            holder.itemView.context.startActivity(intentDetailFood)
        }
    }

class MainViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root)

override fun getItemCount(): Int {
    return kulinerList.size
}

    companion object {

        const val NAME_FOOD = "NAME_FOOD"
        const val DESC_FOOD = "DESC_FOOD"
        const val PHOTO_FOOD = "PHOTO_FOOD"
        const val PRICE_FOOD = "PRICE_FOOD"
        const val LAT_FOOD = "LAT_FOOD"
        const val LON_FOOD = "LON_FOOD"
    }
}


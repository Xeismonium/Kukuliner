package com.bangkit.kukuliner.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.bangkit.kukuliner.databinding.FoodItemBinding
import com.bangkit.kukuliner.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class MainAdapter(private val onFavoriteClick: (CulinaryResponseItem) -> Unit) :
    ListAdapter<CulinaryResponseItem, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val culinary = getItem(position)
        holder.bind(culinary)

        val ivFavorite = holder.binding.foodFavorite
        if (culinary.isFavorite) {
            ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    ivFavorite.context,
                    R.drawable.heart_fill
                )
            )
        } else {
            ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    ivFavorite.context,
                    R.drawable.heart
                )
            )
        }
        ivFavorite.setOnClickListener {
            onFavoriteClick(culinary)
            notifyItemChanged(position)
        }
    }

    class MainViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(culinary: CulinaryResponseItem) {
            binding.foodName.text = culinary.name
            binding.foodPrice.text = culinary.estimatePrice
            Glide.with(itemView.context)
                .load(culinary.photoUrl)
                .into(binding.foodImage)

            itemView.setOnClickListener {
                val intentDetailFood = Intent(itemView.context, DetailActivity::class.java)
                intentDetailFood.putExtra("Culinary", culinary)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.foodImage, "image"),
                        Pair(binding.foodFavorite, "favorite"),
                    )

                itemView.context.startActivity(intentDetailFood, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CulinaryResponseItem> =
            object : DiffUtil.ItemCallback<CulinaryResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: CulinaryResponseItem,
                    newItem: CulinaryResponseItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: CulinaryResponseItem,
                    newItem: CulinaryResponseItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
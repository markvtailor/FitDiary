package com.markvtls.fitdiary.presentation.food.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.databinding.FoodListItemBinding

class FoodListAdapter(private val onItemClick: (FoodServing) -> Unit): ListAdapter<FoodServing, FoodListAdapter.FoodViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val holder = FoodViewHolder(
            FoodListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    class FoodViewHolder(private var binding: FoodListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(foodServing: FoodServing) {
            binding.apply {
                foodName.text = foodServing.name
                foodCcal.text = foodServing.ccal.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<FoodServing>() {
            override fun areItemsTheSame(oldItem: FoodServing, newItem: FoodServing): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FoodServing, newItem: FoodServing): Boolean {
                return oldItem.id == oldItem.id
            }

        }
    }
}
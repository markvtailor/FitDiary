package com.markvtls.fitdiary.profile.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.fitdiary.databinding.UserProfileOverviewListItemBinding
import com.markvtls.fitdiary.profile.domain.model.DayOverview

class UserProfileOverviewAdapter :
    ListAdapter<DayOverview, UserProfileOverviewAdapter.OverviewViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        return OverviewViewHolder(
            UserProfileOverviewListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OverviewViewHolder(private var binding: UserProfileOverviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dayOverview: DayOverview) {
            binding.apply {
                date.text = dayOverview.date
                steps.text = dayOverview.steps
                caloriesConsumed.text = dayOverview.ccalConsumed
                caloriesBurnt.text = dayOverview.ccalBurned
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DayOverview>() {
            override fun areContentsTheSame(oldItem: DayOverview, newItem: DayOverview): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: DayOverview, newItem: DayOverview): Boolean {
                return oldItem.date == newItem.date
            }
        }
    }
}
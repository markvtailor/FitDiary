package com.markvtls.fitdiary.profile.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.databinding.UserProfileOverviewListItemBinding
import com.markvtls.fitdiary.profile.domain.model.DayOverview

class UserProfileOverviewAdapter(private val context: Context) :
    ListAdapter<DayOverview, UserProfileOverviewAdapter.OverviewViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        return OverviewViewHolder(
            UserProfileOverviewListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OverviewViewHolder(private var binding: UserProfileOverviewListItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dayOverview: DayOverview) {
            binding.apply {
                date.text = context.getString(R.string.overview_date, dayOverview.date)
                steps.text = context.getString(R.string.overview_steps, dayOverview.steps)
                caloriesConsumed.text = context.getString(R.string.overview_ccal_consumed, dayOverview.ccalConsumed)
                caloriesBurnt.text = context.getString(R.string.overview_ccal_burn, dayOverview.ccalBurned)
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
package com.markvtls.fitdiary.pedometer.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.fitdiary.databinding.UserActivityListItemFragmentBinding
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain

class UserActivityListAdapter: ListAdapter<StepActivityDomain, UserActivityListAdapter.UserActivityViewHolder>(
    DiffCallback
) {


    class UserActivityViewHolder(private var binding: UserActivityListItemFragmentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stepActivityDomain: StepActivityDomain) {
            binding.apply {
                date.text = stepActivityDomain.date
                steps.text = stepActivityDomain.steps.toString()
                calories.text = stepActivityDomain.calories.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<StepActivityDomain>() {
            override fun areItemsTheSame(
                oldItem: StepActivityDomain,
                newItem: StepActivityDomain
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StepActivityDomain,
                newItem: StepActivityDomain
            ): Boolean {
                return oldItem.steps == newItem.steps
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserActivityViewHolder {
        return UserActivityViewHolder(
            UserActivityListItemFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserActivityViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}
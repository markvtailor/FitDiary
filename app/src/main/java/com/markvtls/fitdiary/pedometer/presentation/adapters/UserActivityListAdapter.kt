package com.markvtls.fitdiary.pedometer.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.fitdiary.databinding.UserActivityListItemFragmentBinding
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain

class UserActivityListAdapter(private val stepsGoal: Int): ListAdapter<StepActivityDomain, UserActivityListAdapter.UserActivityViewHolder>(
    DiffCallback
) {


    class UserActivityViewHolder(private var binding: UserActivityListItemFragmentBinding, private val stepsGoal: Int): RecyclerView.ViewHolder(binding.root) {
        fun bind(stepActivityDomain: StepActivityDomain) {
            binding.apply {
                date.text = stepActivityDomain.date
                calories.text = stepActivityDomain.calories.toString()
                pedometerProgressBar.text = stepActivityDomain.steps.toString()
                pedometerProgressBar.progress = (stepActivityDomain.steps / (stepsGoal / 100)).toFloat()
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
            UserActivityListItemFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            stepsGoal
        )
    }

    override fun onBindViewHolder(holder: UserActivityViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}
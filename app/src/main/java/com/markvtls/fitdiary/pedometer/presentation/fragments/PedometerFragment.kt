package com.markvtls.fitdiary.pedometer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.markvtls.fitdiary.databinding.UserActivityFragmentBinding
import com.markvtls.fitdiary.pedometer.presentation.PedometerViewModel
import com.markvtls.fitdiary.pedometer.presentation.adapters.PedometerListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PedometerFragment : Fragment() {

    private val viewModel: PedometerViewModel by viewModels()
    private var stepsGoal = 10000
    private var _binding: UserActivityFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserActivityFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.preferences.collect {
                    stepsGoal = it.stepsGoal
                    loadList()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadList() {
        val recyclerView = binding.stepsList
        recyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, true)
        val adapter = PedometerListAdapter(stepsGoal)
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.currentWeekSteps.collect {
                    adapter.submitList(it)
                }
            }
        }
    }


}
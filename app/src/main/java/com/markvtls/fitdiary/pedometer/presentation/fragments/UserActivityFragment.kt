package com.markvtls.fitdiary.pedometer.presentation.fragments

import android.content.Intent
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
import com.markvtls.fitdiary.pedometer.presentation.ActivityViewModel
import com.markvtls.fitdiary.pedometer.presentation.adapters.UserActivityListAdapter
import com.markvtls.fitdiary.pedometer.presentation.services.StepActivityService
import com.markvtls.fitdiary.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserActivityFragment: Fragment() {

    private val viewModel: ActivityViewModel by viewModels()

    private var _binding: UserActivityFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserActivityFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadList()
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        //getSteps()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    private fun getSteps() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.currentDaySteps.collect {
                    try {
                        binding.currentSteps.text = it.steps.toString()
                    } catch (e: Exception) {
                        binding.currentSteps.text = "0"
                    }
                }
            }
        }
    }*/


    private fun loadList() {
        val recyclerView = binding.stepsList
        recyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,true)
        val adapter = UserActivityListAdapter()
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.currentWeekSteps.collect {
                        adapter.submitList(it)
                        println(it.first().date)
                    }
            }
        }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(),StepActivityService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }


}
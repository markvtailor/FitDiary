package com.markvtls.fitdiary.profile.presentation.fragments

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
import com.markvtls.fitdiary.databinding.UserProfileOverviewFragmentBinding
import com.markvtls.fitdiary.profile.presentation.UserProfileViewModel
import com.markvtls.fitdiary.profile.presentation.adapters.UserProfileOverviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileOverviewFragment: Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: UserProfileOverviewFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserProfileOverviewFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadList()


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadList() {
        val recyclerView = binding.foodList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val overviewListAdapter = UserProfileOverviewAdapter()
        recyclerView.adapter = overviewListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.weekOverview.observe(viewLifecycleOwner) {
                    overviewListAdapter.submitList(it)
                }
            }
        }
    }

}
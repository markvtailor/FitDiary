package com.markvtls.fitdiary.profile.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.markvtls.fitdiary.databinding.UserProfileSettingsFragmentBinding
import com.markvtls.fitdiary.profile.presentation.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileSettingsFragment: Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: UserProfileSettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserProfileSettingsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pedometerToggle.setOnClickListener {
            saveSettings()
        }

        viewModel.settings.asLiveData().observe(viewLifecycleOwner) {value ->
            binding.pedometerToggle.isChecked = value.pedometerState
        }
        binding.save.setOnClickListener {
            saveSettings()
            val action = UserProfileSettingsFragmentDirections.actionUserProfileSettingsFragmentToUserProfileFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun saveSettings() {
        lifecycleScope.launch {
            viewModel.saveNewSettings(binding.pedometerToggle.isChecked, requireContext())
        }
    }
}
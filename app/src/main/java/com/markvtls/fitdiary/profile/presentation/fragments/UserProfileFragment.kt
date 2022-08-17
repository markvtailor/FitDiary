package com.markvtls.fitdiary.profile.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.markvtls.fitdiary.databinding.UserProfileFragmentBinding
import com.markvtls.fitdiary.pedometer.presentation.services.PedometerService
import com.markvtls.fitdiary.profile.presentation.UserProfileViewModel
import com.markvtls.fitdiary.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserProfileFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toSettings.setOnClickListener {
            val action =
                UserProfileFragmentDirections.actionUserProfileFragmentToUserProfileSettingsFragment()
            findNavController().navigate(action)
        }

        binding.toPreferences.setOnClickListener {
            val action =
                UserProfileFragmentDirections.actionUserProfileFragmentToUserProfilePreferencesFragment()
            findNavController().navigate(action)
        }

        binding.toOverview.setOnClickListener {
            val action =
                UserProfileFragmentDirections.actionUserProfileFragmentToUserProfileOverviewFragment()
            findNavController().navigate(action)
        }
        viewModel.settings.asLiveData().observe(viewLifecycleOwner) { settings ->
            sendCommandToPedometerService(
                Constants.ACTION_START_OR_RESUME_SERVICE,
                settings.pedometerState
            )
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun sendCommandToPedometerService(action: String, pedometerState: Boolean) {
        if (pedometerState) {
            Intent(requireContext(), PedometerService::class.java).also {
                it.action = action
                requireContext().startService(it)
            }
        }
    }
}
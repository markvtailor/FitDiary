package com.markvtls.fitdiary.profile.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.markvtls.fitdiary.databinding.UserProfilePersonalPreferencesFragmentBinding
import com.markvtls.fitdiary.profile.presentation.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfilePreferencesFragment : Fragment() {


    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: UserProfilePersonalPreferencesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserProfilePersonalPreferencesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.preferences.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.caloriesText.setText(value.ccalGoal.toString(), TextView.BufferType.EDITABLE)
            binding.stepsText.setText(value.stepsGoal.toString(), TextView.BufferType.EDITABLE)
        }

        binding.stepsText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                savePreferences()
        }
        binding.caloriesText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                savePreferences()
        }
        binding.save.setOnClickListener {
            savePreferences()
            val action =
                UserProfilePreferencesFragmentDirections.actionUserProfilePreferencesFragmentToUserProfileFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun savePreferences() {
        lifecycleScope.launch {
            viewModel.saveNewPreferences(
                binding.caloriesText.text.toString().toInt(),
                binding.stepsText.text.toString().toInt(),
                requireContext()
            )
        }
    }

}
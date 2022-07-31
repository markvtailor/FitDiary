package com.markvtls.fitdiary.ui.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.data.FoodServingViewModel
import com.markvtls.fitdiary.data.FoodServingViewModelFactory
import com.markvtls.fitdiary.databinding.FoodDetailsFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FoodDetailsFragment: Fragment() {
    private val navigationArgs: FoodDetailsFragmentArgs by navArgs()

    private val viewModel: FoodServingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "Activity is not created yet!"
        }
        ViewModelProvider(this, FoodServingViewModelFactory(activity.application))[FoodServingViewModel::class.java]
    }

    private var _binding: FoodDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.food_details_fragment,container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom.visibility = View.INVISIBLE
        binding.viewModel ?: viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val id = navigationArgs.id
        lifecycleScope.launch {
            viewModel.getOneById(id).collect {
                bind(it)
            }
        }
        binding.foodDeleteButton.setOnClickListener {
            val action = FoodDetailsFragmentDirections.actionFoodDetailsFragmentToFoodListFragment()
            findNavController().navigate(action)
            viewModel.deleteFood(id)
        }
        binding.foodEditButton.setOnClickListener {
            val action = FoodDetailsFragmentDirections.actionFoodDetailsFragmentToAddFoodFragment(id)
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom.visibility = View.VISIBLE
        _binding = null
    }

    fun bind(foodServing: FoodServing) {
        binding.apply {
            foodName.text = foodServing.name
            foodDetailsServing.text = foodServing.amount.toString()
            foodDetailsCcal.text = foodServing.ccal.toString()
            foodDetailsProtein.text = foodServing.protein.toString()
            foodDetailsCarbon.text = foodServing.carbon.toString()
            foodDetailsFat.text = foodServing.fat.toString()
        }
    }

}
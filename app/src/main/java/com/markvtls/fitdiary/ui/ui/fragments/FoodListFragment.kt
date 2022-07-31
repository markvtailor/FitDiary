package com.markvtls.fitdiary.ui.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.data.FoodServingViewModel
import com.markvtls.fitdiary.data.FoodServingViewModelFactory
import com.markvtls.fitdiary.databinding.FoodListFragmentBinding
import com.markvtls.fitdiary.ui.ui.adapters.FoodListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class FoodListFragment: Fragment() {

    private val navigationArgs: FoodListFragmentArgs by navArgs()

    private val viewModel: FoodServingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "Activity is not created yet!"
        }
        ViewModelProvider(this, FoodServingViewModelFactory(activity.application))[FoodServingViewModel::class.java]
    }


    private var _binding: FoodListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener("requestKey",this) { _, bundle ->
            val result = bundle.getString("bundleKey")
            if (result == "done") {
                refreshUi()
                 } }


            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FoodListFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshUi()
        binding.addFoodButton.setOnClickListener {
            this.findNavController().navigate(FoodListFragmentDirections.actionFoodListFragmentToAddFoodFragment())
        }

        binding.foodListToolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.calendar -> {
                    showDatePickerDialog(view)
                    true
                } else -> false
            }
        }

    }


    private fun showDatePickerDialog(view: View) {
        val newFragment = DatePickerDialogFragment()
        newFragment.show(childFragmentManager, "DatePicker")

    }

    private fun refreshUi() {
        val recyclerView = binding.foodList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val foodListAdapter = FoodListAdapter {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }
        recyclerView.adapter = foodListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.foodlist.collect {
                    foodListAdapter.submitList(it)
                }
            }
        }

        val date = viewModel.chosenDate ?: viewModel.currentDate
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {

                    viewModel.getCcalSum(date).collect() {
                        binding.foodCcalLabel.text = getString(R.string.Ccal, it)
                    }
                }catch (e: Exception) {
                    binding.foodCcalLabel.text = getString(R.string.Ccal, 0)
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        println("Destroyed")
        _binding = null
    }

    fun notifyAboutRequestResult(cause: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Snackbar.make(requireView(), "Ошибка при добавлении: $cause", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
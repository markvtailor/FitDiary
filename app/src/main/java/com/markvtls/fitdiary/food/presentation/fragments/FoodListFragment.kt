package com.markvtls.fitdiary.food.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.databinding.FoodListFragmentBinding
import com.markvtls.fitdiary.food.presentation.FoodServingViewModel
import com.markvtls.fitdiary.food.presentation.adapters.FoodListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodListFragment : Fragment() {


    private val viewModel: FoodServingViewModel by viewModels()
    private var ccalPreference = 2100
    private var _binding: FoodListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val result = bundle.getString("bundleKey")
            if (result == "done") {
                refreshUi()
            } else if (result!!.toInt() > 0) {
                addNewFood(result.toInt())
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FoodListFragmentBinding.inflate(layoutInflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.preferences.collect {
                    ccalPreference = it.ccalGoal
                }
            }
        }
        refreshUi()
        binding.addFoodButton.setOnClickListener {
            addNewFood(0)
        }

        binding.foodListToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.calendar -> {
                    showDatePickerDialog()
                    true
                }
                else -> false
            }
        }
        viewModel.status.observe(viewLifecycleOwner) {
            when (it.case) {
                "SUCCESS" -> println()
                "INVALID WORD" -> notifyAboutRequestResult(it.case)
                "INVALID TOKEN" -> notifyAboutRequestResult(it.case)
                "CONNECTION IS MISSING" -> notifyAboutRequestResult(it.case)
            }
        }
    }


    private fun showDatePickerDialog() {
        val newFragment = DatePickerDialogFragment()
        newFragment.show(childFragmentManager, "DatePicker")

    }

    private fun refreshUi() {
        val recyclerView = binding.foodList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val foodListAdapter = FoodListAdapter {
            showDetails(it.id)
        }
        val divider = DividerItemDecoration(recyclerView.context,LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = foodListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.foodList.collect {
                    foodListAdapter.submitList(it)
                }
            }
        }

        val date = viewModel.chosenDate ?: viewModel.currentDate
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {

                    viewModel.getCcalSum(date).collect {
                        if (it > ccalPreference) binding.foodCcalLabel.setTextColor(
                            resources.getColor(
                                R.color.red
                            )
                        ) else binding.foodCcalLabel.setTextColor(resources.getColor(R.color.black))
                        binding.foodCcalLabel.text = getString(R.string.Ccal, it.toString())
                    }
                } catch (e: Exception) {
                    binding.foodCcalLabel.text = "Ккал: 0"
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        println(viewModel.status.value)
        _binding = null
    }

    private fun addNewFood(id: Int) {
        val dialogFragment = AddFoodDialogFragment()
        val args = Bundle()
        args.putInt("FoodId", id)
        dialogFragment.arguments = args
        dialogFragment.show(childFragmentManager, AddFoodDialogFragment.TAG)
    }

    private fun showDetails(id: Int) {
        val dialogFragment = FoodDetailsDialogFragment()
        val args = Bundle()
        args.putInt("FoodId", id)
        dialogFragment.arguments = args
        dialogFragment.show(childFragmentManager, FoodDetailsDialogFragment.TAG)
    }

    private fun notifyAboutRequestResult(cause: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Snackbar.make(requireView(), "Ошибка при добавлении: $cause", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.notificationCheck()
    }
}
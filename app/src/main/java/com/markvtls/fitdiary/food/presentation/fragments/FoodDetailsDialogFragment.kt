package com.markvtls.fitdiary.food.presentation.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.presentation.FoodServingViewModel
import com.markvtls.fitdiary.databinding.FoodDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodDetailsDialogFragment: DialogFragment() {

    private val viewModel: FoodServingViewModel by viewModels({requireParentFragment()})


    private var _binding: FoodDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FoodDetailsFragmentBinding.inflate(inflater,container,false)
            .apply {
                this.lifecycleOwner = this@FoodDetailsDialogFragment
                this.viewModel = viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("FoodId",0)!!
        if (dialog != null) dialog!!.window!!.setLayout(1100, 1000) ///fix

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getOneById(id).collect {
                    bind(it)
                }
            }
        }
        binding.foodDeleteButton.setOnClickListener {
            dialog?.dismiss()
            viewModel.deleteFood(id)
        }
        binding.foodEditButton.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to "$id"))
            dialog?.dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }

    private fun bind(foodServing: FoodServing) {
        binding.apply {
            foodName.text = foodServing.name
            foodDetailsServing.text = foodServing.amount.toString()
            foodDetailsCcal.text = foodServing.ccal.toString()
            foodDetailsProtein.text = foodServing.protein.toString()
            foodDetailsCarbon.text = foodServing.carbon.toString()
            foodDetailsFat.text = foodServing.fat.toString()
        }
    }

    companion object {
        const val TAG = "Details Dialog"
    }
}
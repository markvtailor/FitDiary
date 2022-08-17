package com.markvtls.fitdiary.food.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.markvtls.fitdiary.databinding.FoodAddToListFragmentBinding
import com.markvtls.fitdiary.food.presentation.FoodServingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddFoodDialogFragment : DialogFragment() {


    private val viewModel: FoodServingViewModel by viewModels({ requireParentFragment() })


    private var _binding: FoodAddToListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodAddToListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (dialog != null) dialog!!.window!!.setLayout(1000, 675) ///fix
        binding.submitButton.setOnClickListener {
            if (isInputValid()) {
                addFoodToList()
                dialog?.dismiss()
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addFoodToList() {

        val id = arguments?.getInt("FoodId", 0)!!
        val name = binding.foodNameInput.text.toString()
        val amount = binding.foodAmountInput.text.toString()
        lifecycleScope.launch {
            viewModel.getNutrition(id, name, amount.toInt())
        }
    }

    private fun isInputValid(): Boolean {
        val nameInput = binding.foodNameInput
        val amountInput = binding.foodAmountInput
        var conditions = 0
        if (nameInput.text.isNullOrBlank()) {
            nameInput.error = "Заполните поле"
            conditions++
        }

        if (amountInput.text.isNullOrBlank()) {
            amountInput.error = "Заполните поле"
            conditions++
        }

        if (!nameInput.text.toString().matches("\\s*\\p{L}+\\s*\\p{L}*\\s*\\p{L}*\\s*".toRegex())) {
            nameInput.error = "Введите слово!"
            conditions++
        }

        if (!amountInput.text.toString().matches("[0-9]{1,4}".toRegex())) {
            amountInput.error = "Некорректное число!"
            conditions++
        }
        return conditions == 0
    }

    companion object {
        const val TAG = "Addition Dialog"
    }
}
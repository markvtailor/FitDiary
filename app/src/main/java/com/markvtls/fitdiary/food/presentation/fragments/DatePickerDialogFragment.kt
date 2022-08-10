package com.markvtls.fitdiary.food.presentation.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.markvtls.fitdiary.food.presentation.FoodServingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: FoodServingViewModel by viewModels(ownerProducer = { requireParentFragment()})


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            viewModel.chooseDate(day, month, year)
            viewModel.getFoodListByDate(viewModel.chosenDate!!)

        }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult("requestKey", bundleOf("bundleKey" to "done"))
    }


}
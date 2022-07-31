package com.markvtls.fitdiary.ui.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.data.FoodServingViewModel
import com.markvtls.fitdiary.data.FoodServingViewModelFactory

import com.markvtls.fitdiary.databinding.FoodAddToListFragmentBinding
import com.markvtls.fitdiary.utils.InvalidWordException
import kotlinx.coroutines.launch
import java.net.UnknownHostException


class AddFoodFragment: Fragment() {
    private val navigationArgs: AddFoodFragmentArgs by navArgs()

    private val viewModel: FoodServingViewModel by navGraphViewModels(R.id.nav_graph) {
         FoodServingViewModelFactory(requireActivity().application)
    }

    private var _binding: FoodAddToListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FoodAddToListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom.visibility = View.INVISIBLE
        binding.submitButton.setOnClickListener{
            if (isInputValid())   addFoodToList()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom.visibility = View.VISIBLE
        _binding = null
    }

    private fun addFoodToList() {
        val id = navigationArgs.id
        val name = binding.foodNameInput.text.toString()
        val amount = binding.foodAmountInput.text.toString()
        viewModel.getNutrition(id, name, amount.toInt())
        val action = AddFoodFragmentDirections.actionAddFoodFragmentToFoodListFragment(true)
        findNavController().navigate(action)

    }
    private fun isAdditionSuccessful(addFoodToList: () -> Unit) {
        try {
            addFoodToList.invoke()
        }  catch (e: InvalidWordException) {
        println(e.printStackTrace())
        notifyAboutRequestResult("Неверное слово") //заменить на ресурсы
    } catch (e: UnknownHostException) {
        println(e.printStackTrace())
        notifyAboutRequestResult("Отсутствует подключение")
    } catch (e: Exception) {
        println(e.printStackTrace())
        notifyAboutRequestResult("Токен истек")
    } }

    fun notifyAboutRequestResult(cause: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Snackbar.make(requireView(), "Ошибка при добавлении: $cause", Snackbar.LENGTH_LONG).show()
            }
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
1
        if (!amountInput.text.toString().matches("[0-9]{1,4}".toRegex())) {
            amountInput.error = "Некорректное число!"
            conditions++
        }
        return conditions == 0
    }


}
package com.markvtls.fitdiary.presentation.food

import androidx.lifecycle.*
import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.data.dto.FoodNutrition
import com.markvtls.fitdiary.data.dto.TranslationRequest
import com.markvtls.fitdiary.domain.use_cases.food_use_cases.DeleteFoodUseCase
import com.markvtls.fitdiary.domain.use_cases.food_use_cases.*
import com.markvtls.fitdiary.utils.InvalidWordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FoodServingViewModel @Inject constructor(
    private val translate: TranslateNameUseCase,
    private val nutrition: GetFoodNutritionUseCase,
    private val insert: InsertFoodUseCase,
    private val delete: DeleteFoodUseCase,
    private val getAll: GetCompleteFoodListUseCase,
    private val getByDate: GetFoodListByDateUseCase,
    private val getOneFoodById: GetOneFoodByIdUseCase,
    private val getCalories: GetCaloriesUseCase
): ViewModel() {

    enum class ExceptionCases(val case: String) {
        SUCCESS("SUCCESS"),
        INVALID_WORD("INVALID WORD"),
        INVALID_TOKEN("INVALID TOKEN"),
        MISSING_CONNECTION("CONNECTION IS MISSING")
    }
    private var _status = MutableLiveData(ExceptionCases.SUCCESS)
    val status: LiveData<ExceptionCases> get() = _status

    private var _currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMy"))
    private var _chosenDate: String? = null
    val chosenDate get() = _chosenDate
    val currentDate get() = _currentDate

    private var _nutritionList: List<FoodNutrition>? = null
    val nutritionList get() = _nutritionList
    private lateinit var _foodList: Flow<List<FoodServing>>
    val foodList get() = _foodList

    init {

        println("init")
        val date = _chosenDate ?: currentDate
        getFoodListByDate(date)
    }

    fun getCcalSum(date: String): Flow<Int> {
            return getCalories(date)
    }

    fun getOneById(id: Int): Flow<FoodServing> {
        return getOneFoodById(id)
    }

    fun deleteFood(foodId: Int) {
        viewModelScope.launch {
            delete(foodId)
        }
    }

    fun getNutrition(id: Int, name: String, amount: Int) {
        getFoodInfo(id, name, amount)
    }

    private  fun getFoodInfo(id: Int, name: String, amount: Int) { //refactoring required
        var translation = ""
        val translate = TranslationRequest(texts = name)
        viewModelScope.launch {
            try {
                this@FoodServingViewModel.translate.invoke(translate).collect {
                    translation = it
                }
                nutrition.invoke(translation).collect {
                    _nutritionList = it
                }
                if (nutritionList.isNullOrEmpty()) throw InvalidWordException("Invalid word")
                insert.invoke(id,name, amount, nutritionList!!, currentDate)
            } catch (e: InvalidWordException) {
                println(e.printStackTrace())
                _status.value = ExceptionCases.INVALID_WORD
            } catch (e: UnknownHostException) {
                println(e.printStackTrace())
                _status.value = ExceptionCases.MISSING_CONNECTION
            } catch (e: Exception) {
                println(e.printStackTrace())
                _status.value = ExceptionCases.INVALID_TOKEN
            }
        }
    }


    private fun getFoodList() {
        _foodList = getAll()
    }

    fun getFoodListByDate(date: String) {
        println("Chosen date: $date")
        _foodList = getByDate(date)
    }

    fun chooseDate(day: Int, month: Int, year: Int) {
        val rawDate = LocalDate.of(year, month + 1, day)
        _chosenDate = rawDate.format(DateTimeFormatter.ofPattern("ddMMy"))
    }

    fun notificationCheck() {
        _status.value = ExceptionCases.SUCCESS
    }



}




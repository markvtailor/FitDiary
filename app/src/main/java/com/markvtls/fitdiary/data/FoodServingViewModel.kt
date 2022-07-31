package com.markvtls.fitdiary.data

import android.app.Application
import androidx.lifecycle.*
import androidx.work.Data
import com.markvtls.fitdiary.network.*
import com.markvtls.fitdiary.utils.InvalidTokenException
import com.markvtls.fitdiary.utils.InvalidWordException
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FoodServingViewModel(application: Application): ViewModel() {

    enum class ExceptionCases(val case: String) {
        SUCCESS("SUCCESS"),
        INVALID_WORD("INVALID WORD"),
        INVALID_TOKEN("INVALID TOKEN"),
        MISSING_CONNECTION("CONNECTION IS MISSING")
    }
    private var _status = MutableLiveData(ExceptionCases.SUCCESS)
    val status: LiveData<ExceptionCases> get() = _status
    private val foodServingRepository =
        FoodServingRepository(FoodServingDatabase.getDatabase(application))
    private var _currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMy"))
    private var _chosenDate: String? = null
    val chosenDate get() = _chosenDate
    val currentDate get() = _currentDate
    lateinit var _currentFood: String
    private var _currentFoodAmount: Int = 0
    val currentFoodAmount get() = _currentFoodAmount
    val currentFood get() = _currentFood
    private var _nutritionList: List<FoodNutrition>? = null
    val nutritionList get() = _nutritionList
    private lateinit var _foodList: Flow<List<FoodServing>>
    val foodlist get() = _foodList

    init {
        println("init")
        val date = _chosenDate ?: currentDate
        getFoodListByDate(date)
    }

    fun getCcalSum(date: String): Flow<Int> {
        return foodServingRepository.getCcalSum(date)
    }

    fun getOneById(id: Int): Flow<FoodServing> {
        return foodServingRepository.getFoodById(id)
    }

    fun deleteFood(foodId: Int) {
        viewModelScope.launch {
            foodServingRepository.deleteFood(foodId)
        }
    }

    fun getNutrition(id: Int, name: String, amount: Int) {
        getFoodInfo(id, name, amount)
    }

    private  fun getFoodInfo(id: Int, name: String, amount: Int) { //refactoring required
        _currentFood = name
        _currentFoodAmount = amount
        val translate = TranslationRequest(texts = name)
        viewModelScope.launch {
            try {
                val translation = TranslationApi.retrofitService.translateName(translate).translations.first().text
                _nutritionList = NutritionApi.retrofitService.getNutritionInfo(translation)
                if (nutritionList.isNullOrEmpty()) throw InvalidWordException("Invalid word")
                foodServingRepository.insertFood(createFoodInstance(id))
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

    private fun createFoodInstance(id: Int): FoodServing {
        println(nutritionList)
        val food = FoodServing(id, currentFood, currentFoodAmount, 0.0, 0.0, 0.0, 0.0, currentDate)
        nutritionList?.forEach { it ->
            food.ccal += it.calories.toDouble()
            food.protein += it.protein_g.toDouble()
            food.carbon += it.carbohydrates_total_g.toDouble()
            food.fat += it.fat_total_g.toDouble()
        }
        food.apply {
            ccal *= currentFoodAmount * 0.01
            protein *= currentFoodAmount * 0.01
            carbon *= currentFoodAmount * 0.01
            fat *= currentFoodAmount * 0.01
        }
        val formatter =
            NumberFormat.getInstance(Locale.US) ///What to do with number like 1,234,400.5?
        food.apply {
            ccal = formatter.format(ccal).replace(",", "").toDouble()
            protein = formatter.format(protein).replace(",", "").toDouble()
            carbon = formatter.format(carbon).replace(",", "").toDouble()
            fat = formatter.format(fat).replace(",", "").toDouble()
        }
        return food
    }

    private fun getFoodList() {
        _foodList = foodServingRepository.foodList
    }

    fun getFoodListByDate(date: String) {
        println("Chosen date: $date")
        _foodList = foodServingRepository.getFoodByDate(date)
    }

    fun chooseDate(day: Int, month: Int, year: Int) {
        val rawDate = LocalDate.of(year, month + 1, day)
        _chosenDate = rawDate.format(DateTimeFormatter.ofPattern("ddMMy"))
    }

    fun notificationCheck() {
        _status.value = ExceptionCases.SUCCESS
    }



}

class FoodServingViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodServingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodServingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


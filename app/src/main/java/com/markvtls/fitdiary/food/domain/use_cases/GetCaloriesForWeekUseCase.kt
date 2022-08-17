package com.markvtls.fitdiary.food.domain.use_cases


import com.markvtls.fitdiary.food.domain.model.CaloriesForDay
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCaloriesForWeekUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(): Flow<List<CaloriesForDay>> = flow {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-y")
        val dates = mutableListOf<String>()
        val overview = mutableListOf<CaloriesForDay>()
        val map = mutableMapOf<String, String>()
        for (i in 0..6) {
            val currentDate = LocalDate.now().minusDays(i.toLong()).format(formatter).toString()
            dates.add(currentDate)
            map[currentDate] = "0"
        }
        val ccal = repository.getAllForCurrentWeek(dates)
        ccal.collect { list ->
            list.forEach { listItem ->
                map[listItem.date] = listItem.ccal
            }
            map.forEach { (date, ccal) ->
                overview.add(CaloriesForDay(date, ccal))
                println("$date $ccal")
            }
            emit(overview)
        }
    }
}
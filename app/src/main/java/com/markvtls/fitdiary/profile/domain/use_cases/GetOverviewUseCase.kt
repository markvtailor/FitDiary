package com.markvtls.fitdiary.profile.domain.use_cases

import com.markvtls.fitdiary.food.domain.model.CaloriesForDay
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.data.source.local.toDomain
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import com.markvtls.fitdiary.profile.domain.model.DayOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetOverviewUseCase @Inject constructor(
    private val foodRepository: FoodServingRepository,
    private val pedometerRepository: StepActivityRepository
) {
    operator fun invoke(): Flow<List<DayOverview>> = flow {
        val overview = mutableListOf<DayOverview>()
        getSteps().collect{ stepsList ->
            getCcal().collect {ccalList ->
                stepsList.forEach { steps ->
                    if (steps.date in ccalList.map { it.date }.toList()) {
                        overview.add(
                            DayOverview(
                                steps.date,
                                steps.steps.toString(),
                                ccalList[ccalList.indexOf(ccalList.firstOrNull { it.date == steps.date })].ccal,
                                steps.calories.toString()
                            )
                        )
                    } else {
                        overview.add(
                            DayOverview(
                                steps.date,
                                steps.steps.toString(),
                                "0",
                                steps.calories.toString()
                            )
                        )
                    }
                }
                println(overview.size)
                emit(overview)
            }
        }
    }


    private fun getSteps(): Flow<List<StepActivityDomain>> = flow {
        val stepsDomain = mutableListOf<StepActivityDomain>()
        pedometerRepository.getAllForCurrentWeek().collect { list ->
            stepsDomain.addAll(list.map { it.toDomain() })
            emit(stepsDomain)
        }
    }

    private fun getCcal()= flow {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-y")
        val dates = mutableListOf<String>()
        val overview = mutableListOf<CaloriesForDay>()
        val map = mutableMapOf<String, String>()
        for (i in 0..6) {
            val currentDate = LocalDate.now().minusDays(i.toLong()).format(formatter).toString()
            dates.add(currentDate)
            map[currentDate] = "0"
        }
        val ccal = foodRepository.getAllForCurrentWeek(dates)
        ccal.collect { list ->
            list.forEach { listItem ->
                map[listItem.date] = listItem.ccal
            }
            map.forEach { (date, ccal) ->
                overview.add(CaloriesForDay(date, ccal))
            }
            emit(overview)
        }
    }
}
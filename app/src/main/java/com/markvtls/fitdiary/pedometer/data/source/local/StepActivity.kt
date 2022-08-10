package com.markvtls.fitdiary.pedometer.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
class StepActivity(
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val steps: Int
)

data class DateHolder (val date: String)


fun StepActivity.toDomain(): StepActivityDomain {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val year = "${date[0]}${date[1]}${date[2]}${date[3]}".toInt()
    val month = "${date[4]}${date[5]}".toInt()
    val day = "${date[6]}${date[7]}".toInt()
    val dateDomain = LocalDate.of(year, month, day).format(formatter).toString()
    //val dateDomain = date
    val calories = steps / 25

    return StepActivityDomain(dateDomain, steps, calories)
}
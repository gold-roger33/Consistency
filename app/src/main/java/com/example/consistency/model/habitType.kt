package com.example.consistency.model

sealed class HabitType{
    data class Hourly(
        val TotalHours: Int,
        val currentTime: Int,
        ) : HabitType()

    data class Numerical(
        val totalNumber :Int,
        val currentNumber :Int
    ) : HabitType()
}
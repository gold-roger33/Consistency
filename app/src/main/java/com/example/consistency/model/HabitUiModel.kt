package com.example.consistency.model

import com.example.consistency.data.entity.Habit

data class HabitUiModel(
    val id: Int = 0,
    val name: String,
    val target: Int,
    val done: Int = 0,
    val isPaused: Boolean = false,
    val totalStreakDays: Int = 0 ,
    val isCompleted: Boolean = done >= target,
    val progressPercentage: Float = done.toFloat() / target
)

fun Habit.toUiModel(): HabitUiModel {
return HabitUiModel(
    id = id,
    name = habitName,
    target = totalTarget,
    done = numberDone,
    isPaused = isPaused,
    totalStreakDays = totalStreakDays,
    isCompleted = numberDone >= totalTarget,
    progressPercentage = numberDone.toFloat() / totalTarget
)
}

fun HabitUiModel.toEntity() : Habit{
    return Habit(
        id = id,
        habitName = name,
        totalTarget = target,
        numberDone = done,
        isPaused = isPaused,
        totalStreakDays = totalStreakDays
    )
}
package com.example.consistency.model

import com.example.consistency.data.entity.Habit

data class HabitUiModel(
    val id: Int = 0,
    val name: String,
    val target: Long,
    val progress: Long = 0L,
    val isTimeBased:Boolean = false,
    val isPaused: Boolean = true,
    val totalStreakDays: Int = 0,
    val isCompleted: Boolean = progress >= target,
    val progressPercentage: Long = if (target > 0) (progress * 100 / target) else 0L,
    val unitTypeData: UnitType
){
    val remaining: Long
        get() = (target - progress).coerceAtLeast(0L) // avoid negatives
}

fun Habit.toUiModel(): HabitUiModel {
return HabitUiModel(
    id = id,
    name = habitName,
    target = totalTarget,
    progress = currentProgress,
    isPaused = isPaused,
    totalStreakDays = totalStreakDays,
    isCompleted = currentProgress >= totalTarget,
    progressPercentage = currentProgress / totalTarget,
    isTimeBased = isTimeBased,
    unitTypeData = unitTypeData
)
}

fun HabitUiModel.toEntity() : Habit{
    return Habit(
        id = id,
        habitName = name,
        totalTarget = target,
        currentProgress = progress,
        isPaused = isPaused,
        totalStreakDays = totalStreakDays,
        isTimeBased = isTimeBased,
        unitTypeData = unitTypeData
    )
}
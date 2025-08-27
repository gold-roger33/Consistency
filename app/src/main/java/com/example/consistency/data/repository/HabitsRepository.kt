package com.example.consistency.data.repository

import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    fun getTaskStream() : Flow<List<Habit>>

    suspend fun addTask(data :Habit)

    suspend fun deleteTask(data: Habit)
}
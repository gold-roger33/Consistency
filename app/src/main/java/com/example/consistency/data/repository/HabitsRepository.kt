package com.example.consistency.data.repository

import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    fun getTaskStream() : Flow<List<Habit>>

    fun getTaskById(id: Int) :Habit

    suspend fun addTask(data :Habit)

    suspend fun deleteTask(data: Habit)

    suspend fun updateTask(data: Habit)
}
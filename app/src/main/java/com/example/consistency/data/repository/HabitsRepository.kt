package com.example.consistency.data.repository

import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    fun getTaskStream() : Flow<List<Habit>>

    fun getPausedHabits() : Flow<List<Habit>>

    fun getActiveHabits() : Flow<List<Habit>>

    suspend fun getTaskById(id: Int) :Habit

    suspend fun addTask(data :Habit)

    suspend fun deleteTask(id: Int)

    suspend fun updateTask(data: Habit)

    suspend fun pauseTask(id: Int)

    suspend fun resumeTask(id : Int)


}
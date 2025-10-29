package com.example.consistency.data.repository

import com.example.consistency.data.dao.TaskDao
import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow

class HabitTrackerRepository (
private val taskDao: TaskDao
) : HabitsRepository{
    override fun getTaskStream(): Flow<List<Habit>> = taskDao.getAllTask()

    override suspend fun addTask(data: Habit) =  taskDao.addNewTask(data)

    override suspend fun getTaskById(id: Int): Habit = taskDao.getTaskById(id)

    override fun getPausedHabits(): Flow<List<Habit>> = taskDao.getPausedHabits()

    override fun getActiveHabits():  Flow<List<Habit>> = taskDao.getActiveHabits()

    override suspend fun updateTask(data: Habit) = taskDao.updateTask(data)

    override suspend fun deleteTask(id: Int) = taskDao.deleteHabit(id)

    override suspend fun pauseTask(id: Int) = taskDao.pauseHabit(id)

    override suspend fun resumeTask(id: Int) = taskDao.resumeHabit(id)
}
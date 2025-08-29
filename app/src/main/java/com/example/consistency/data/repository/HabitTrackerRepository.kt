package com.example.consistency.data.repository

import com.example.consistency.data.dao.TaskDao
import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow

class HabitTrackerRepository (
private val taskDao: TaskDao
) : HabitsRepository{
    override fun getTaskStream(): Flow<List<Habit>> = taskDao.getAllTask()

    override suspend fun addTask(data: Habit) =  taskDao.addNewTask(data)

    override suspend fun deleteTask(data: Habit) = taskDao.deleteHabit(data.habitName)

    override fun getTaskById(id: Int): Habit = taskDao.getTaskById(id)

    override suspend fun updateTask(data: Habit) = taskDao.updateTask(data)

}
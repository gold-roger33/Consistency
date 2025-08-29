package com.example.consistency.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao{

    @Insert
    suspend fun addNewTask(data : Habit)

    @Update
    suspend fun updateTask(data: Habit)

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getTaskById(id: Int): Habit

    @Query("SELECT * FROM habit")
    fun getAllTask(): Flow<List<Habit>>

    @Query("DELETE FROM habit WHERE id = :ID")
    suspend fun deleteHabit(ID : Int)

    @Query("UPDATE habit SET isPaused = 1 WHERE id =:ID")
    suspend fun pauseHabit(ID :Int)

    @Query("UPDATE habit SET isPaused = 0 WHERE id =:ID")
    suspend fun resumeHabit(ID :Int)

    @Query("SELECT * FROM habit WHERE isPaused = 1")
    fun getPausedHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE isPaused = 0")
    fun getActiveHabits(): Flow<List<Habit>>

}

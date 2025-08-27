package com.example.consistency.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.consistency.data.entity.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao{

    @Insert
    suspend fun addNewTask(data : Habit)

    @Query("SELECT * FROM habit")
    fun getAllTask(): Flow<List<Habit>>

    @Query("DELETE FROM habit WHERE habitName =:habitName")
    suspend fun deleteHabit(habitName:String)


}

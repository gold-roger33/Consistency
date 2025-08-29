package com.example.consistency.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consistency.model.HabitType

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitName: String,
    val totalTarget:Int, // total no for the current task

    val numberDone: Int = 0,  //no of task completed today by the user

    val isPaused: Boolean = false, //task pause or not

    val totalStreakDays : Int = 0,

)

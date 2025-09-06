package com.example.consistency.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consistency.model.HabitType

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitName: String,
    
    val totalTarget:Float, //  Could be in minutes or counts

    val numberDone: Float = 0F,  //no of task completed today by the user

    val isTimeBased: Boolean = false, // True = time-based, False = count-based

    val isPaused: Boolean = false, //task pause or not

    val totalStreakDays : Int = 0,

)

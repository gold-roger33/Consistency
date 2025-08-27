package com.example.consistency.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consistency.model.HabitType

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitName: String,
    val number:Int,
    val totalStreakDays : Int,

)

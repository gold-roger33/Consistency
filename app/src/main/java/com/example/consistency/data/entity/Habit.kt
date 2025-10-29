package com.example.consistency.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consistency.model.UnitType

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitName: String,
    
    val totalTarget:Long, //  Could be in minutes or counts

    val currentProgress: Long = 0L,  //progress of a habit

    val isTimeBased: Boolean = false, // True = time-based, False = count-based

    val isPaused: Boolean = false, //task pause or not

    val totalStreakDays : Int = 0,

    val unitTypeData: UnitType //which str type is the habit
                          // eg:"minute","Rep"
)

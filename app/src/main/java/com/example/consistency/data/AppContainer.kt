package com.example.consistency.data

import android.content.Context
import com.example.consistency.data.database.AppDatabase
import com.example.consistency.data.repository.HabitTrackerRepository
import com.example.consistency.data.repository.HabitsRepository


interface AppContainer {
 val habitsRepository : HabitsRepository
}

class AppDataContainer( private val context: Context) : AppContainer{

    override val habitsRepository: HabitsRepository by lazy{
        HabitTrackerRepository(
            AppDatabase.getDatabase(context).taskDao()
        )
    }

}
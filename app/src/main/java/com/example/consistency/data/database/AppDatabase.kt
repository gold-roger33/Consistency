package com.example.consistency.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.consistency.data.dao.TaskDao
import com.example.consistency.data.entity.Habit

@Database(entities = [Habit::class], version = 1)
abstract  class AppDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object{
        @Volatile
        private var Instance :AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            return  Instance?: synchronized(this){
            Room.databaseBuilder(context,
                AppDatabase::class.java,name = "habit_database")
                .fallbackToDestructiveMigration()
                .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
                .build()
                .also { Instance = it }
            }
        }
    }
}
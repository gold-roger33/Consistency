package com.example.consistency.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.consistency.ConsistencyApplication
import com.example.consistency.data.entity.Habit
import com.example.consistency.data.repository.HabitsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val habitsRepository: HabitsRepository
): ViewModel() {

    private val _allHabitsList = MutableStateFlow<List<Habit>>(emptyList())
    val allHabits :StateFlow<List<Habit>> = _allHabitsList


    init {
        viewModelScope.launch {
            habitsRepository.getTaskStream().collect(){habits ->
                _allHabitsList.value = habits
            }
        }
    }

    fun incProgress(habit: Habit) {
        if (habit.numberDone < habit.totalTarget) {
            val updated = habit.copy(numberDone = habit.numberDone + 1)
            viewModelScope.launch {
                habitsRepository.updateTask(updated)
            }
        }
    }

    fun addNewTask(){

    }

    fun onTaskPaused(){

    }

    fun onTaskResume(){

    }

    fun onTaskCompleted(){

    }

    fun calcuateActiveTask(){

    }

    fun taskDoneToday(){

    }
    fun totalStreak(){

    }

        companion object{

    val factory :ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val application = (this[AndroidViewModelFactory.APPLICATION_KEY] as ConsistencyApplication)
        HomeScreenViewModel(
            application.container.habitsRepository
        )
    }
    }
}
}
package com.example.consistency.ui.screen

import android.util.MutableBoolean
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.consistency.ConsistencyApplication
import com.example.consistency.data.entity.Habit
import com.example.consistency.data.repository.HabitsRepository
import com.example.consistency.model.HabitUiModel
import com.example.consistency.model.toEntity
import com.example.consistency.model.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val habitsRepository: HabitsRepository
): ViewModel() {

    private val _allHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val allHabits : StateFlow<List<HabitUiModel>> = _allHabitsList

    private val _pausedHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val pausedHabits : StateFlow<List<HabitUiModel>> = _pausedHabitsList

    private val _activeHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val activeHabits : StateFlow<List<HabitUiModel>> = _activeHabitsList

    private val _showDialog : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog : StateFlow<Boolean> = _showDialog


    init {
        viewModelScope.launch {
            habitsRepository.getTaskStream().collect() { habits ->
                val allHabits = habits.map { it.toUiModel() }

                _allHabitsList.value = allHabits
                _activeHabitsList.value = allHabits.filter { !it.isPaused }
                _pausedHabitsList.value = allHabits.filter { it.isPaused }
            }
        }
    }

    fun showDialog(value : Boolean){
        _showDialog.value = value
    }

    fun incProgress(habitUi: HabitUiModel) {
        if (habitUi.done < habitUi.target) {
            val updated = habitUi.copy(done = habitUi.done + 1)
            viewModelScope.launch {
                habitsRepository.updateTask(updated.toEntity())
            }
        }
    }

    fun addNewTask(habitName: String, totalTarget: Int, unit: String){
        val newHabit = HabitUiModel(
            name = habitName,
            target = totalTarget
        )
        viewModelScope.launch {
            habitsRepository.addTask(newHabit.toEntity())
        }
    }

    fun deleteTask(habit : HabitUiModel){
        viewModelScope.launch {
            habitsRepository.deleteTask(habit.id)
        }
    }

    fun onTaskPaused(habit: HabitUiModel){
        viewModelScope.launch {
            habitsRepository.pauseTask(habit.id)
        }
    }

    fun onTaskResume(habit: HabitUiModel){
        viewModelScope.launch {
            habitsRepository.resumeTask(habit.id)
        }

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
package com.example.consistency.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.consistency.ConsistencyApplication
import com.example.consistency.data.repository.HabitsRepository
import com.example.consistency.model.HabitUiModel
import com.example.consistency.model.TimerInfo
import com.example.consistency.model.TimerState
import com.example.consistency.model.UnitType
import com.example.consistency.model.toEntity
import com.example.consistency.model.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.apply

private const val VIEWTAG = "viewmodel"

class HomeScreenViewModel(
    private val habitsRepository: HabitsRepository
): ViewModel() {

    private val _allHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val allHabits : StateFlow<List<HabitUiModel>> = _allHabitsList

    private val _pausedHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val pausedHabits : StateFlow<List<HabitUiModel>> = _pausedHabitsList

    private val _activeHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val activeHabits : StateFlow<List<HabitUiModel>> = _activeHabitsList

    private val _completedHabitsList = MutableStateFlow<List<HabitUiModel>>(emptyList())
    val completedHabits : StateFlow<List<HabitUiModel>> = _completedHabitsList

    private val _showDialog : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog : StateFlow<Boolean> = _showDialog

    private val _sliderPositions = MutableStateFlow<Map<Int, Float>>(emptyMap())
    val sliderPositions: StateFlow<Map<Int, Float>> = _sliderPositions

    private var _currentRunningHabitId: Int? = null

    private val _timers = MutableStateFlow<Map<Int, TimerInfo>>(emptyMap())
    val timers: StateFlow<Map<Int, TimerInfo>> = _timers

    private val timerJob = mutableMapOf<Int, Job>()

    init {
        viewModelScope.launch {
            habitsRepository.getTaskStream().collect() { habits ->
                val allHabits = habits.map { it.toUiModel() }

                _allHabitsList.value = allHabits
                _activeHabitsList.value = allHabits.filter { !it.isPaused }
                _pausedHabitsList.value = allHabits.filter { it.isPaused }
                _completedHabitsList.value = allHabits.filter { it.isCompleted }

            }
        }
        Log.d(VIEWTAG,"viewModel init")
    }

    fun updateSlider(habitId: Int, position: Float){
        _sliderPositions.value = _sliderPositions.value.toMutableMap().apply {
            this[habitId] = position
        }
    }

    fun getSliderPosition(habitId: Int): Float {
        return _sliderPositions.value[habitId] ?: 0f
    }

    fun showDialog(value : Boolean){
        _showDialog.value = value
    }



    fun incProgress(habitUi: HabitUiModel) {
        if (habitUi.progress < habitUi.target) {
            val updated = habitUi.copy(progress = habitUi.progress + 1)
            viewModelScope.launch {
                habitsRepository.updateTask(updated.toEntity())
            }
        }
    }

    fun decProgress(habitUi: HabitUiModel){
        if (habitUi.progress > 0) {
            val updated = habitUi.copy(progress = habitUi.progress -1)
            viewModelScope.launch {
                habitsRepository.updateTask(updated.toEntity())
            }
        }
    }

    fun addNewTask(habitName: String,
                   totalTarget: Long,
                   unit: String,
                   isTimeBased:Boolean,
                   unitTypeData: UnitType){
        val newHabit = HabitUiModel(
            name = habitName,
            target = totalTarget,
            isTimeBased = isTimeBased,
            unitTypeData = unitTypeData
        )
        viewModelScope.launch {
            habitsRepository.addTask(newHabit.toEntity())
        }
        Log.d(VIEWTAG,"addNewTaskCompleted")
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




    //Number of  ActiveHabits for StreakCards Compose
    fun calculateActiveTask() :Int{
        return activeHabits.value.size
    }



    fun startTimer(habit: HabitUiModel) {
        // Pause any other running timer
        _currentRunningHabitId?.let { runningId ->
            if (runningId != habit.id) stopTimerForId(runningId,
                habit.target)

        }

        if (timerJob.containsKey(habit.id)) return // already running

        val current = _timers.value[habit.id]
        var timeLeft = current?.remainingTime ?: (habit.target * 60_000L)

        if (timeLeft <= 0) return

        val job = viewModelScope.launch(Dispatchers.Default) {
            updateTimerState(habit.id, TimerState.RUNNING, isRunning = true, timeLeft)

            while (timeLeft > 0 && isActive) {
                delay(1000)
                timeLeft -= 1000
                updateTimerTime(habit.id, timeLeft)
            }

            if (timeLeft <= 0) {
                updateTimerState(habit.id, TimerState.STOPPED, isRunning = false, 0)
                timerJob.remove(habit.id)
                _currentRunningHabitId = null
            }
        }

        timerJob[habit.id] = job
        _currentRunningHabitId = habit.id
    }

    fun pauseTimer(habit: HabitUiModel) {
        timerJob[habit.id]?.cancel()
        timerJob.remove(habit.id)

        val info = _timers.value[habit.id]
        updateTimerState(
            habit.id,
            TimerState.PAUSED,
            isRunning = false,
            remainingTime = info?.remainingTime ?: (habit.target * 60_000L)
        )

        _currentRunningHabitId = null
    }

    fun stopTimer(habit: HabitUiModel) = stopTimerForId(habit.id,
        habit.target * 60_000L)

    private fun stopTimerForId(habitId: Int,totalTime: Long) {
        timerJob[habitId]?.cancel()
        timerJob.remove(habitId)

        updateTimerState(
            habitId,
            TimerState.STOPPED,
            isRunning = false,
            remainingTime = totalTime
        )

        if (_currentRunningHabitId == habitId) _currentRunningHabitId = null
    }

    private fun updateTimerTime(habitId: Int, timeLeft: Long) {
        _timers.value = _timers.value.toMutableMap().apply {
            val old = this[habitId] ?: TimerInfo()
            this[habitId] = old.copy(remainingTime = timeLeft)
        }
    }

    private fun updateTimerState(
        habitId: Int,
        state: TimerState,
        isRunning: Boolean,
        remainingTime: Long
    ) {
        _timers.value = _timers.value.toMutableMap().apply {
            this[habitId] = TimerInfo(
                remainingTime = remainingTime,
                state = state,
                isRunning = isRunning
            )
        }
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
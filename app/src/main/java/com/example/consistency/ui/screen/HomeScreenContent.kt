package com.example.consistency.ui.screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.consistency.model.HabitUiModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.compose.greensih
import com.example.consistency.model.TimerInfo
import com.example.consistency.model.TimerState
import com.example.consistency.model.UnitType
import com.example.consistency.model.toEntity

@Composable
fun HomeScreenContent(
    activeHabitsNumber: Int,
    allHabits: List<HabitUiModel>,
    activeHabits: List<HabitUiModel>,
    pausedHabits: List<HabitUiModel>,
    showDialog: Boolean,
    onPauseResume: (HabitUiModel) -> Unit,
    onDelete: (HabitUiModel) -> Unit,
    onAddHabitClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    onIncrement: (HabitUiModel) -> Unit,
    onDecrement: (HabitUiModel) -> Unit,
    showProgressControls: (HabitUiModel) -> Boolean,
    onDialogCreate: (String, Long, String, Boolean, UnitType) -> Unit,
    modifier: Modifier = Modifier,
    sliderPosition: Map<Int, Float>,
    onSliderChange: (habitId: Int, newValue: Float) -> Unit,
    onTimerStart: (HabitUiModel) -> Unit,
    onTimerPause: (HabitUiModel) -> Unit,
    onTimerStop: (HabitUiModel) -> Unit,
    timers: Map<Int, TimerInfo>,
    )
{
    Log.d("HomeScreenContent", "Active habits count: $activeHabitsNumber")

    Scaffold(
        topBar = { TopBar() },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ) {
            item {
                StreakStatus(
                    modifier = modifier,
                    activeHabitsNumber = activeHabitsNumber
                )
                Spacer(modifier = Modifier.size(12.dp))

                AddNewHabitButton(onClick = onAddHabitClick)
                Spacer(modifier = Modifier.size(12.dp))

                HabitLabel("Active Habits", activeHabits.size, greensih)
            }

            items(activeHabits) { habit ->
                val safeSlider = sliderPosition[habit.id]?.takeIf { !it.isNaN() }
                    ?: ((habit.progress.toFloat() / habit.target.toFloat()).takeIf {
                        !it.isNaN() } ?: 0f)

                HabitsListCard(
                    challengeName = habit.toEntity(),
                    isPaused = false,
                    streakDays = 100,
                    onHabitPausedOrResume = { onPauseResume(habit) },
                    onDelete = { onDelete(habit) },
                    completePercentage = 60L,
                    showProgressControls = !habit.isPaused,
                    onIncrement = { onIncrement(habit) },
                    onDecrement = { onDecrement(habit) },
                    sliderPosition = safeSlider,
                    onSliderChange = { newValue -> onSliderChange(habit.id, newValue) },
                    unitTypeData = habit.unitTypeData,
                    isTimeBased = habit.isTimeBased,
                    onTimerStart = { onTimerStart(habit) },
                    onTimerPause = { onTimerPause(habit) },
                    onTimerStop = { onTimerStop(habit) },
                    timerInfo = timers[habit.id],
                )
            }

            item {
                HabitLabel("Paused", pausedHabits.size, MaterialTheme.colorScheme.tertiaryContainer)
            }

            items(pausedHabits) { habit ->
                HabitsListCard(
                    challengeName = habit.toEntity(),
                    isPaused = true,
                    streakDays = 100,
                    onHabitPausedOrResume = { onPauseResume(habit) },
                    onDelete = { onDelete(habit) },
                    completePercentage = 60L,
                    showProgressControls = false,
                    onDecrement = {},
                    onIncrement = { },
                    sliderPosition = sliderPosition[habit.id]
                        ?: (habit.progress.toFloat() / habit.target),
                    onSliderChange = { },
                    unitTypeData = habit.unitTypeData,
                    isTimeBased = habit.isTimeBased,
                    onTimerStart = { onTimerStart(habit) },
                    onTimerPause = { onTimerPause(habit) },
                    onTimerStop = { onTimerStop(habit) },
                    timerInfo = timers[habit.id],

                )
            }
        }
    }

    if (showDialog) {
        NewHabitDialog(
            onDismiss = onDialogDismiss,
            onCreate = onDialogCreate
        )
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val mockHabits = listOf(
        HabitUiModel(
            id = 1,
            name = "Read",
            target = 30L,
            progress = 15L,
            isPaused = false,
            unitTypeData = UnitType.REPS,
        ),
        HabitUiModel(id = 2,
            name = "Workout",
            target = 20L,
            progress = 5L,
            isPaused = true,
            unitTypeData = UnitType.MINUTES
        )
    )
    val mockSliderPositions = mapOf(
        1 to 0.5f, // 15/30
        2 to 0.25f // 5/20
    )
    val mockTimers = mapOf(
        1 to TimerInfo(remainingTime = 14L * 60_000L + 30_000L,
            isRunning = true,
            state = TimerState.RUNNING),
        2 to TimerInfo(remainingTime = 2L * 60_000L + 30_000L,
            isRunning = false,
            state = TimerState.PAUSED)
    )

    AppTheme {
    HomeScreenContent(
        allHabits = mockHabits,
        activeHabits = mockHabits.filter { !it.isPaused },
        pausedHabits = mockHabits.filter { it.isPaused },
        showDialog = false,
        onPauseResume = {},
        onDelete = {},
        onAddHabitClick = {},
        onDialogDismiss = {},
        onDialogCreate = { _, _, _, _, _ -> },
        activeHabitsNumber = 1,
        onDecrement = { },
        showProgressControls = { habit -> !habit.isPaused },
        onIncrement = { },
        sliderPosition = mockSliderPositions,
        onSliderChange = { _, _ -> },

        onTimerStart = {  },
        onTimerPause = {  },
        onTimerStop = {  },
        timers = mockTimers,
    )
}
}
package com.example.consistency.ui.screen

import android.content.res.Configuration
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
    onDialogCreate: (String, Int, String) -> Unit,
    modifier: Modifier = Modifier,
    sliderPosition: Map<Int, Float>,
    onSliderChange: (habitId: Int, newValue: Float) -> Unit,

) {
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
                HabitsListCard(
                    challengeName = habit.toEntity(),
                    isPaused = false,
                    streakDays = 100,
                    onPausedOrResume = { onPauseResume(habit) },
                    onDelete = { onDelete(habit) },
                    completePercentage = 60,
                    showProgressControls = !habit.isPaused,
                    onIncrement = { onIncrement(habit) },
                    onDecrement = { onDecrement(habit) },
                    sliderPosition = sliderPosition[habit.id] ?:
                                    (habit.done.toFloat() / habit.target),
                    onSliderChange = { newValue -> onSliderChange(habit.id, newValue) }
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
                    onPausedOrResume = { onPauseResume(habit) },
                    onDelete = { onDelete(habit) },
                    completePercentage = 60,
                    showProgressControls = false,
                    onDecrement = {},
                    onIncrement = { },
                    sliderPosition = sliderPosition[habit.id] ?:
                    (habit.done.toFloat() / habit.target),
                    onSliderChange = { },

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
        HabitUiModel(id = 1, name = "Read", target = 30, done = 15, isPaused = false),
        HabitUiModel(id = 2, name = "Workout", target = 20, done = 5, isPaused = true)
    )
    val mockSliderPositions = mapOf(
        1 to 0.5f, // 15/30
        2 to 0.25f // 5/20
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
        onDialogCreate = { _, _, _ -> },
        activeHabitsNumber = 1,
        onDecrement = { },
        showProgressControls = { habit -> !habit.isPaused },
        onIncrement = { },
        sliderPosition = mockSliderPositions,
        onSliderChange =  { _, _ -> },
    )
}
}
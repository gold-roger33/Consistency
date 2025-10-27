package com.example.consistency.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.sharp.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.addButtonGradColour
import com.example.compose.amoledBlack
import com.example.compose.sliderColour
import com.example.consistency.R
import com.example.consistency.data.entity.Habit
import com.example.consistency.model.TimerInfo
import com.example.consistency.model.TimerState
import com.example.consistency.model.UnitType

@Composable
fun  HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory),
    modifier: Modifier = Modifier
) {
    val allHabits by viewModel.allHabits.collectAsState()
    val activeHabits by viewModel.activeHabits.collectAsState()
    val pausedHabits by viewModel.pausedHabits.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()
    val sliderPositions by viewModel.sliderPositions.collectAsState()
    val timers by viewModel.timers.collectAsState()




    HomeScreenContent(
        activeHabitsNumber = viewModel.calculateActiveTask(),
        allHabits = allHabits,
        activeHabits = activeHabits,
        pausedHabits = pausedHabits,
        showDialog = showDialog,
        onPauseResume = { //for each habit
            if (it.isPaused) viewModel.onTaskResume(it) else viewModel.onTaskPaused(it)
        },
        onDelete = { viewModel.deleteTask(it) },
        onAddHabitClick = { viewModel.showDialog(true) },
        onDialogDismiss = { viewModel.showDialog(false) },
        onDialogCreate = { name, target, unit, isTimeBased, unitTypeData ->
            viewModel.addNewTask(
                name, target, unit, isTimeBased, unitTypeData
            )
            viewModel.showDialog(false)
        },
        onDecrement = { viewModel.decProgress(it) },
        onIncrement = { viewModel.incProgress(it) },
        showProgressControls = { habit -> !habit.isPaused },
        sliderPosition = sliderPositions,
        onSliderChange = { habitId, value ->
            viewModel.updateSlider(habitId, value)
        },
        modifier = modifier,
        onTimerStart = { habit ->
            viewModel.startTimer(habit) },
        onTimerPause = {  habit ->
            viewModel.pauseTimer(habit) },
        onTimerStop = {  habit ->
            viewModel.stopTimer(habit) },
        timers = timers,
    )
}


        @Composable
        fun StreakStatus(
            activeHabitsNumber: Int,
            modifier: Modifier = Modifier
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {

                StreakCards(
                    days = activeHabitsNumber,
                    descriptions = "Active",
                    IconsImage = R.drawable.thunder,
                )
                StreakCards(
                    days = 6,
                    descriptions = "Done Today",
                    IconsImage = R.drawable.calendar,

                    )
                StreakCards(
                    days = 7,
                    descriptions = "Total Streak",
                    IconsImage = R.drawable.redfire,
                )
            }

        }

        @Composable
        fun HabitLabel(
            title: String,
            count: Int,
            colour: Color,
            modifier: Modifier = Modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                modifier = modifier.fillMaxWidth()
            ) {

                Text(
                    text = title,
                    modifier = modifier,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                )
                Card(
                    shape = RoundedCornerShape(9.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colour
                    ),
                    modifier = modifier
                        .padding(9.dp)
                        .size(25.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = count.toString(),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(2.dp)

                        )
                    }
                }
            }

        }

        @SuppressLint("DefaultLocale")
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun HabitsListCard(
            challengeName: Habit,
            unitTypeData: UnitType,
            isPaused: Boolean,
            streakDays: Int,
            onHabitPausedOrResume: () -> Unit,
            onDelete: () -> Unit,
            completePercentage: Long,
            showProgressControls: Boolean = true, //habit paused or not
            onDecrement: () -> Unit,
            onIncrement: () -> Unit,
            sliderPosition: Float,
            onSliderChange: (Float) -> Unit,
            isTimeBased: Boolean,

            onTimerStart: () -> Unit,
            onTimerPause: () -> Unit,
            onTimerStop: () -> Unit,
            timerInfo: TimerInfo?,
            modifier: Modifier = Modifier
        ) {
            Log.d(
                "SliderDebug",
                "Habit: ${challengeName.habitName}, " +
                        "numberDone=${challengeName.currentProgress}, " +
                        "totalTarget=${challengeName.totalTarget}"
            )

            val totalTarget = challengeName.totalTarget

            val (progressFraction, displayProgress) = if (isTimeBased) {
                val totalTime = totalTarget * 60_000L
                val remainingTime = timerInfo?.remainingTime ?: totalTime
                val elapsed = totalTime - remainingTime
                val fraction = (elapsed.toFloat() / totalTime).coerceIn(0f, 1f)
                val currentProgress = (elapsed / 60_000L).toInt()
                fraction to "$currentProgress/${totalTarget.toInt()} min"
            } else {
                val fraction = sliderPosition.coerceIn(0f, 1f)
                fraction to "${challengeName.currentProgress.toInt()}/${totalTarget.toInt()} ${challengeName.unitTypeData.label}"
            }

                Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = challengeName.habitName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                onHabitPausedOrResume()
                                if (isTimeBased && timerInfo?.isRunning == true){
                                    onTimerPause()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isPaused) Icons.Default.PlayArrow else
                                    Icons.Default.Pause, //need to update fot time based
                                contentDescription = "Pause"
                            )
                        }

                        IconButton(
                            onClick = { onDelete()
                            if (isTimeBased) onTimerStop()
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Habit"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()

                    ) {
                        Icon(
                            imageVector = Icons.Sharp.AccessTime,
                            contentDescription = "Time"

                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = displayProgress,
                            // need to change for time-based and it is directly calling from habit data class
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Image(
                            painter = painterResource(R.drawable.yellow_fire),
                            contentDescription = "fire",
                            modifier = modifier
                                .size(20.dp)
                                .padding(2.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "$streakDays day streak",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    // var sliderPosition by remember { mutableFloatStateOf(completePercentage / 100f ) }

                    Column(
                        //horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = sliderPosition)
                                    .height(15.dp)
                                    .background(sliderColour, shape = RoundedCornerShape(50))
                            )

                            Slider(
                                value = progressFraction,
                                onValueChange = {
                                    if (!isTimeBased) onSliderChange(it)
                                                },
                                enabled = !isTimeBased,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.Transparent,
                                    activeTrackColor = Color.Transparent,
                                    inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Text(
                            text = "${(progressFraction * 100).toInt()}% Completed",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    if (showProgressControls) {
                        if (isTimeBased) {
                            val remainingTime = timerInfo?.remainingTime ?:  (challengeName.totalTarget * 60_000L)
                            val minutes = (remainingTime / 1000) / 60
                            val seconds = (remainingTime / 1000) % 60
                            val timerRunning = timerInfo?.isRunning == true

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = String.format("%d:%02d", minutes, seconds),
                                        style = MaterialTheme.typography.bodyLarge,
                                        //fontWeight = FontWeight.Bold,
                                        color = Color.Cyan,
                                        modifier = Modifier.padding(horizontal = 16.dp)

                                    )

                                   Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = when (timerInfo?.state) {
                                            TimerState.RUNNING -> "Timer Running..."
                                            TimerState.PAUSED -> "Paused"
                                            TimerState.STOPPED -> "Stopped"
                                            else -> "Idle"
                                        },
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(horizontal = 16.dp)

                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row (
                                        modifier = Modifier
                                            .padding(bottom = 4.dp)
                                    ){
                                        Button(
                                            onClick = {
                                                if (timerRunning) {
                                                    onTimerPause()
                                                } else {
                                                    onTimerStart()
                                                }
                                            }
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                if (timerRunning) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Pause,
                                                        contentDescription = "Pause"
                                                    )
                                                    Spacer(Modifier.width(4.dp))
                                                    Text("Pause")
                                                }else{
                                                    Icon(
                                                        imageVector = Icons.Filled.PlayArrow,
                                                        contentDescription = "Pause"
                                                    )
                                                    Spacer(Modifier.width(4.dp))
                                                    Text("Start")
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Button(
                                                onClick = {
                                                    onTimerStop()
                                                }
                                            ) {
                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ){

                                                    Icon(
                                                        imageVector = Icons.Filled.Stop,
                                                        contentDescription = "Stop  Timer"
                                                    )
                                                   Spacer(Modifier.width(4.dp))
                                                    Text("Stop")
                                                }

                                            }
                                        }
                                    }

                                }

                            }
                        } else {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = modifier
                                    .fillMaxWidth()
                                //.padding(top = 10.dp)
                            ) {

                                IconButton(
                                    onClick = onDecrement
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Minus Sign"
                                    )
                                }

                                Text(
                                    text = "${(challengeName.currentProgress).toInt()}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(horizontal = 16.dp)

                                )

                                IconButton(
                                    onClick = onIncrement
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "ADD Button",
                                    )
                                }
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = modifier
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = "Task is paused",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }

        @Composable
        fun StreakCards(
            @DrawableRes IconsImage: Int,
            days: Int,
            descriptions: String,
            modifier: Modifier = Modifier
        ) {

            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .size(width = 100.dp, height = 150.dp)
//                    .border(
//                        width = 0.5.dp,
//                        color = neonGreen,
//                        shape = RoundedCornerShape(15.dp),
//                    )

            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = amoledBlack)

                ) {
                    Box(
                        modifier = modifier
                            .size(25.dp)
                    ) {
                        Image(
                            painter = painterResource(IconsImage),
                            contentDescription = "Card Icons"
                        )
                    }

                    Text(
                        text = descriptions
                    )

                    Text(
                        text = days.toString()
                    )

                }

            }
        }


        @Composable
        fun AddNewHabitButton(
            onClick: () -> Unit,
            modifier: Modifier = Modifier
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(brush = addButtonGradColour, shape = RoundedCornerShape(20.dp))
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Habit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add New Habit",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun TopBar(modifier: Modifier = Modifier) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Consistency",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = amoledBlack
                ),
            )
        }




        @Preview(showBackground = true)
        @Composable
        fun HabitsListCardPreview() {
            val fakeHabit = Habit(
                id = 1,
                habitName = "Read Books",
                totalTarget = 30L,
                currentProgress = 15L,
                isPaused = false,
                totalStreakDays = 5,
                isTimeBased = true,
                unitTypeData = UnitType.REPS,
            )
            val mockTimerMap =
                TimerInfo(remainingTime = 14L * 60_000L + 30_000L,
                    isRunning = true,
                    state = TimerState.RUNNING)



            HabitsListCard(
                challengeName = fakeHabit,
                isPaused = fakeHabit.isPaused,
                streakDays = fakeHabit.totalStreakDays,
                onHabitPausedOrResume = {},
                onDelete = {},
                completePercentage = (fakeHabit.currentProgress * 100) / fakeHabit.totalTarget,
                modifier = Modifier.padding(16.dp),
                onDecrement = {},
                onIncrement = {},
                showProgressControls = true,
                sliderPosition = 0.5f,
                onSliderChange = {},
                unitTypeData = fakeHabit.unitTypeData,
                isTimeBased = fakeHabit.isTimeBased,
                onTimerStart = {},
                onTimerPause = {},
                onTimerStop = {},
                timerInfo = mockTimerMap,
            )
        }

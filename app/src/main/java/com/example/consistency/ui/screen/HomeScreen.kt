package com.example.consistency.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.sharp.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.amoledBlack
import com.example.consistency.R
import com.example.consistency.data.entity.Habit

@Composable
fun  HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory),
    modifier: Modifier = Modifier
) {
    val habitList by viewModel.allHabits.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    Scaffold(
        topBar = {
            TopBar()
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ) {
            item {

            StreakStatus(
                modifier = modifier
            )
                Spacer(modifier = Modifier.size(12.dp))

            AddNewHabitButton(
                onClick = {
                    viewModel.showDialog(true)
                }
            )
                Spacer(modifier = Modifier.size(12.dp))

            CurrentActiveHabits(
                number = 20,
            )
            }
                items(habitList){habit ->
                    HabitsListCard(
                        challengeName = habit,
                        //habitType = HabitType.Numerical,
                        isDeleted = false,
                        streakDays = 100,
                        onPaused = {
                            viewModel.onTaskPaused()
                        },
                        onDelete = {

                        },
                        completePercentage = 60,
                    )
                }
            }
            }
    if (showDialog){
        NewHabitDialog(
            onDismiss = { viewModel.showDialog(false) },
            onCreate = { name, target, unit ->
                viewModel.addNewTask(name, target, unit)
                viewModel.showDialog(false)
            }
        )
    }
        }


@Composable
fun PausedDetails(
    challengeName: String,
    //habitType: HabitType,
    isDeleted:Boolean,
    streakDays: Int,
    isPaused:Boolean,
    completePercentage:Int,
    modifier: Modifier
){
    Text(
        text = "Paused"
    )


}

@Composable
fun StreakStatus(
    modifier: Modifier = Modifier
){
    Row (
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {

        StreakCards(
            days = "5",
            descriptions = "Active",
            IconsImage = R.drawable.thunder,
        )
        StreakCards(
            days = "6",
            descriptions = "Done Today",
            IconsImage = R.drawable.calendar,

        )
        StreakCards(
            days = "7",
            descriptions = "Total Streak",
            IconsImage = R.drawable.redfire,
        )
    }

}

@Composable
fun CurrentActiveHabits(
    number:Int,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = "Active Habits",
            modifier = modifier,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
        )
        Card(
            shape = RoundedCornerShape(9.dp),
            modifier=modifier
                .padding(9.dp)
                .size(25.dp)
        ) {
        Text(
            text = number.toString(),
            textAlign = TextAlign.Center,
            modifier = modifier.padding(2.dp)

        )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsListCard(
    challengeName: Habit,
    //habitType: HabitType,
    isDeleted:Boolean,
    streakDays: Int,
    onPaused:() -> Unit,
    onDelete: () -> Unit,
    completePercentage:Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
//            .border(
//                width = 0.dp,
//                color = Color.Gray,
//                shape = RoundedCornerShape(8.dp)
//            ),
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
                    onClick = onPaused
                ) {
                    Icon(
                        Icons.Default.Pause,
                        contentDescription = "Pause"
                    )
                }

                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete"
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
                    text = "15/30 minutes"
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
            var sliderPosition by remember { mutableFloatStateOf(completePercentage / 100f ) }

            Column(
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    thumb = {}
                )
                Text(
                    text = "${(sliderPosition * 100).toInt()}% Completed",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    //.padding(top = 10.dp)
            ) {

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Minus Sign"
                    )
                }

                Text(
                    text = streakDays.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)

                )

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "ADD Button",
                    )
                }
            }
        }
    }
}

        @Composable
        fun StreakCards(
            @DrawableRes IconsImage: Int,
            days:String,
            descriptions:String,
            modifier: Modifier = Modifier
        ){

            Card (
                shape = RoundedCornerShape(15.dp),
                modifier = modifier
                    .size(width = 100.dp, height = 150.dp)

            ){
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize()

                ) {
                    Box (
                        modifier = modifier
                            .size(25.dp)
                    ){
                        Image(
                            painter = painterResource(IconsImage),
                            contentDescription = "Card Icons"
                        )
                    }

                    Text(
                        text = descriptions
                    )

                    Text(
                        text = days
                    )

                }

            }
        }


        @Composable
        fun AddNewHabitButton(
            onClick: ()  -> Unit,
            modifier: Modifier = Modifier
        ){
            Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors =ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Habit",
                       )

                    Text(
                        text = "Add New Habit",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun TopBar(modifier: Modifier = Modifier){
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
        fun HomeScreenPreview() {
            MaterialTheme {
                HomeScreen(
                    modifier = Modifier
                )
            }
        }

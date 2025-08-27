package com.example.consistency.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.sharp.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.amoledBlack
import com.example.consistency.R
import com.example.consistency.model.HabitType
import kotlinx.serialization.SerialName
import kotlin.jvm.internal.Ref.BooleanRef

@Composable
fun  HomeScreen(
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            TopBar(modifier = Modifier)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ) {
            StreakStatus(
                modifier = modifier
            )
            AddNewHabitButton(
                modifier,
                onClick = {}
            )
            CurrentActiveHabits(
                number = 20,
                modifier = modifier
            )
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {item {
                HabitsListCard(
                    challengeName = "Solve LeetCode problems",
                    //habitType = HabitType.Numerical,
                    isDeleted = false,
                    streakDays = 100,
                    isPaused = false,
                    completePercentage = 60,
                    modifier = modifier
                )

                HabitsListCard(
                    challengeName = "Read technical articles",
                    isDeleted = false,
                    streakDays = 3,
                    isPaused = false,
                    completePercentage = 50,
                    modifier = modifier
                )
            }
            }
        }
    }
}

@Composable
fun StreakStatus(
    modifier: Modifier
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
            modifier = modifier
        )
        StreakCards(
            days = "6",
            descriptions = "Done Today",
            IconsImage = R.drawable.thunder,
            modifier = modifier
        )
        StreakCards(
            days = "7",
            descriptions = "Total Streak",
            IconsImage = R.drawable.thunder,
            modifier = modifier
        )
    }

}

@Composable
fun CurrentActiveHabits(
    number:Int,
    modifier: Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        modifier=modifier
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

@Composable
fun HabitsListCard(
    challengeName: String,
    //habitType: HabitType,
    isDeleted:Boolean,
    streakDays: Int,
    isPaused:Boolean,
    completePercentage:Int,
    modifier: Modifier
){
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ){
            Text(
                text = challengeName
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                Icons.Default.Pause,
                    contentDescription = "Pause"
                )
            }

            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete")
            }
        }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Sharp.AccessTime,
            contentDescription = "Time"

        )
        Text(
            text = "15/30 minutes"
        )

        Image(
            painter = painterResource(R.drawable.thunder),
            contentDescription = "fire",
            modifier = modifier.size(50.dp)
        )

        Text(
            text = "$streakDays day streak"
        )
    }
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column (
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(
            text = "$sliderPosition % Completed",
            textAlign = TextAlign.Center
        )
    }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Minus Sign"
            )
        }

        Text(
            text = streakDays.toString()
        )

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "ADD Button",
            )}
    }

}

@Composable
fun StreakCards(
    @DrawableRes IconsImage: Int,
    days:String,
    descriptions:String,
    modifier: Modifier
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
                    .size(30.dp)
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
modifier: Modifier,
onClick: ()  -> Unit
){
    Button(
    onClick = {
        onClick
    },
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors =ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add New Habit",
        modifier = modifier
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
fun TopBar(modifier: Modifier){
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
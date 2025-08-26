package com.example.consistency.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.amoledBlack
import kotlinx.serialization.SerialName

@Composable
fun  HomeScreen(
    modifier: Modifier
) {
//TopBar(modifier = modifier)
StreakStatus(modifier = modifier)

}

@Composable
fun StreakStatus(
    modifier: Modifier
){
    Row (
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            //.padding(20.dp)
    ) {

        StreakCards(
                days = "5",
                descriptions = "Active",
                modifier = modifier
        )
        StreakCards(
            days = "6",
            descriptions = "Done Today",
            modifier = modifier
        )
        StreakCards(
            days = "7",
            descriptions = "Total Streak",
            modifier = modifier
        )
    }

}


@Composable
fun StreakCards(
    //IconsImage:StringRes,
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()

        ) {

            Text(
                text = descriptions
            )

            Text(
                text = days
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


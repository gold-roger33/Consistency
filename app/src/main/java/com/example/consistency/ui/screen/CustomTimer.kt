    package com.example.consistency.ui.screen

    import androidx.compose.foundation.BorderStroke
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Card
    import androidx.compose.material3.Text
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp


    //@Preview(showBackground = true)
    @Composable
    fun CustomTimer(
        selectedHour: Int,
        selectedMinute: Int,
        onHourChange: (Int) -> Unit,
        onMinuteChange: (Int) -> Unit
    ){
        Card(
            shape = RoundedCornerShape(9.dp),
            border = BorderStroke(1.dp, color = Color.Black),
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Hours")
                    LazyColumn(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(24) { hour ->
                            Text(
                                text = hour.toString(),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(20.dp)
                                    .clickable {
                                        onHourChange(hour)
                                    },
                                color = if (selectedHour == hour) Color.Cyan else Color.Unspecified
                            )
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Minutes")
                    LazyColumn(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(60) { minute ->
                            Text(
                                text = minute.toString().padStart(2, '0'),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(20.dp)
                                    .clickable {
                                       onMinuteChange(minute)
                                    },
                                color = if (selectedMinute == minute) Color.Cyan else Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
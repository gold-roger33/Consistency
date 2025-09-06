package com.example.consistency.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.consistency.model.Type
import com.example.consistency.model.UnitType

@Composable
fun NewHabitDialogContent(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onCreate: (String, Float, String,Boolean) -> Unit
) {
    var description by remember { mutableStateOf("") }
    var target by remember {  mutableStateOf<Float?>(null) }
    var unit by remember { mutableStateOf(UnitType.TASKS) }
    var type by remember { mutableStateOf(Type.COUNT) }

    val suggestedUnits = UnitType.entries
    val context = LocalContext.current

    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    Card(
        modifier = modifier
            .wrapContentHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Create New Habit", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Habit description")

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("e.g. 10 Push ups daily") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column {
                Text(text = "Type")

                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier

                ) {
                    Type.entries.forEach { currentType ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { type = currentType }
                        ) {
                            RadioButton(
                                selected = if (unit == UnitType.HOURS
                                    || unit ==UnitType.MINUTES  ){
                                    currentType == Type.TIME
                                }else{
                                    (type == currentType)
                                },
                                onClick = { type = currentType }
                            )
                            val icon = when (currentType) {
                                Type.TIME -> Icons.Default.AccessTime
                                Type.COUNT -> Icons.Default.TrackChanges
                            }

                            Icon(
                                imageVector = icon,
                                contentDescription = currentType.name
                            )

                            Text(
                                text = currentType.description,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                            )
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)

                ) {
                    Text(text = "Target")
                    if (unit == UnitType.MINUTES || unit == UnitType.HOURS ){
                        CustomTimer(
                            selectedHour = selectedHour,
                            selectedMinute = selectedMinute,
                            onHourChange = { selectedHour = it },
                            onMinuteChange = { selectedMinute = it }
                        )
                    }
                    else {
                        OutlinedTextField(
                            value = target?.toString() ?: "",
                            onValueChange = { input ->

                                target = input.toFloatOrNull() ?: if (input.isEmpty()) null else 1F
                            },
                            placeholder = {
                                Text("1")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)) {
                    Text(text = "Unit")
                    OutlinedTextField(
                        value = unit.label,
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            Text(text = "Suggested units:")

                suggestedUnits.forEach { item ->
                    Text(
                        text = item.label,
                        modifier = Modifier
                            .clickable { unit = item },
                        color =  Color.Cyan,
                        textDecoration = TextDecoration.Underline,
                        )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = modifier
                        .fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(2.dp),
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
                OutlinedButton(
                    onClick = {
                        val isTimeBased = unit == UnitType.MINUTES || unit == UnitType.HOURS

                        val finalTarget = if (isTimeBased) {
                            (selectedHour * 60 + selectedMinute).toFloat()
                        } else {
                            target
                        }

                        if (description.isBlank() || finalTarget == null || finalTarget.isNaN() || finalTarget <= 0f) {
                        Toast.makeText(context, "Enter a valid Description or Target", Toast.LENGTH_SHORT).show()
                    } else {
                        onCreate(description, finalTarget, unit.label, isTimeBased)
                    }
                    },
                    modifier = modifier
                        .fillMaxWidth(1f),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(2.dp),
                ) {
                    Text(
                        text = "Create Habit"
                    )
                }
            }
        }
    }
}

@Composable
fun NewHabitDialog(
    modifier: Modifier = Modifier,
    onDismiss : () -> Unit,
    onCreate: (String, Float, String,Boolean) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NewHabitDialogContent(
            modifier,
            onCancel = onDismiss,
            onCreate = { name, target, unit, isTimeBased ->
                onCreate(name, target, unit, isTimeBased)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewHabitDialogContentPreview() {
    MaterialTheme {
        NewHabitDialogContent(
            modifier = Modifier,
            onCancel = {},
            onCreate = {name,target,unit,isTimeBased->

            } ,
        )
    }
}
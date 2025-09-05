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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
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
    onCreate: (String, Int, String) -> Unit
) {
    var description by remember { mutableStateOf("") }
    var target by remember {  mutableStateOf<Int?>(null) }
    var unit by remember { mutableStateOf(UnitType.TIMES) }
    var type by remember { mutableStateOf(Type.COUNT) }

    val suggestedUnits = UnitType.entries
    val context = LocalContext.current

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
                                selected = (type == currentType),
                                onClick = { type = currentType }
                            )
                            Text(
                                text = currentType.description,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Target")
                    OutlinedTextField(
                        value = target?.toString() ?: "",
                        onValueChange = {input ->
                            target = input.toIntOrNull() ?: if (input.isEmpty()) null else 1
                                        },
                        placeholder = {
                            Text("1")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
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
                        if (description.isBlank() || target == null) {
                            Toast.makeText(context,
                                "Enter a vaild Description or Target",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            onCreate(description, target!!, unit.label)
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
    onCreate: (String, Int, String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NewHabitDialogContent(
            modifier,
            onCancel = onDismiss,
            onCreate = { name, target, unit ->
                onCreate(name, target, unit)
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
            onCreate = {name,target,unit ->

            } ,
        )
    }
}
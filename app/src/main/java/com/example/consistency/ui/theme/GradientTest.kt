package com.example.consistency.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.sharp.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import com.example.compose.activeCardColour
import com.example.compose.addButtonGradColour
import com.example.compose.amoledBlack
import com.example.compose.neonGreen
import com.example.compose.sliderColour
import com.example.consistency.R
import com.example.consistency.data.entity.Habit
import com.example.consistency.ui.screen.HabitsListCard
import org.intellij.lang.annotations.Language


@Composable
fun StreakCardsPreview(
    @DrawableRes IconsImage: Int,
    days:String,
    descriptions:String,
    modifier: Modifier = Modifier
) {

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .size(width = 100.dp, height = 150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Blue
        )


    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .size(width = 100.dp, height = 150.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Vertical gradient: top & bottom to center
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color(0xFF00FF88),
                                    0.1f to Color(0xFF000000),
                                    0.5f to Color(0xFF000000),
                                    0.9f to Color(0xFF000000),
                                    1.0f to Color(0xFF00FF88)
                                )
                            )
                        )
                )

                // Horizontal gradient: left & right to center
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color(0xFF00FF88),
                                    0.1f to Color(0xFF000000),
                                    0.5f to Color(0xFF000000),
                                    0.9f to Color(0xFF000000),
                                    1.0f to Color(0xFF00FF88)
                                )
                            )
                        )
                )
            }
        }

                // Your content
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
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
                        text = days
                    )

                }

            }
        }



@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
            showBackground = true)
@Composable
fun GradeintTest() {

    StreakCardsPreview(
        days = "5",
        descriptions = "Active",
        IconsImage = R.drawable.thunder,
    )

}
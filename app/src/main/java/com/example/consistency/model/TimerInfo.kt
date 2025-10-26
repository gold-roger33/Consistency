package com.example.consistency.model

data class TimerInfo(
    val remainingTime: Long = 0L,
    val state: TimerState = TimerState.IDLE,
    val isRunning: Boolean = false
)
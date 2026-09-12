package com.example.firstkotlinapp.data

import java.time.LocalDate

data class ToDoTask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

data class Habit(
    val id: Int,
    val name: String,
    val streak: Int = 0,
    val isCompletedToday: Boolean = false
)

data class CalendarEvent(
    val id: Int,
    val title: String,
    val date: LocalDate,
    val description: String = ""
)

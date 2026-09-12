package com.example.firstkotlinapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.firstkotlinapp.data.CalendarEvent
import com.example.firstkotlinapp.data.Habit
import com.example.firstkotlinapp.data.ToDoTask
import java.time.LocalDate

class MainViewModel : ViewModel() {
    // To-Do State
    var tasks = mutableStateOf(listOf(
        ToDoTask(1, "Buy groceries"),
        ToDoTask(2, "Finish Android project"),
        ToDoTask(3, "Call Mom")
    ))
    var newTaskTitle = mutableStateOf("")

    fun addTask() {
        if (newTaskTitle.value.isNotBlank()) {
            val newId = (tasks.value.maxOfOrNull { it.id } ?: 0) + 1
            tasks.value = tasks.value + ToDoTask(newId, newTaskTitle.value)
            newTaskTitle.value = ""
        }
    }

    fun toggleTask(taskId: Int, isCompleted: Boolean) {
        tasks.value = tasks.value.map {
            if (it.id == taskId) it.copy(isCompleted = isCompleted) else it
        }
    }

    fun deleteTask(taskId: Int) {
        tasks.value = tasks.value.filter { it.id != taskId }
    }

    // Habit State
    var habits = mutableStateOf(listOf(
        Habit(1, "Drink 2L Water", 5, true),
        Habit(2, "Read 20 pages", 3, false),
        Habit(3, "Morning Run", 10, true)
    ))
    var newHabitName = mutableStateOf("")

    fun addHabit() {
        if (newHabitName.value.isNotBlank()) {
            val newId = (habits.value.maxOfOrNull { it.id } ?: 0) + 1
            habits.value = habits.value + Habit(newId, newHabitName.value)
            newHabitName.value = ""
        }
    }

    fun toggleHabit(habitId: Int) {
        habits.value = habits.value.map {
            if (it.id == habitId) {
                val newCompleted = !it.isCompletedToday
                it.copy(
                    isCompletedToday = newCompleted,
                    streak = if (newCompleted) it.streak + 1 else it.streak - 1
                )
            } else it
        }
    }

    // Calendar State
    var events = mutableStateOf(listOf(
        CalendarEvent(1, "Dentist Appointment", LocalDate.now(), "Routine checkup"),
        CalendarEvent(2, "Project Meeting", LocalDate.now().plusDays(1), "Discuss architecture"),
        CalendarEvent(3, "Birthday Party", LocalDate.now().plusDays(2), "Gift required")
    ))
    var newEventTitle = mutableStateOf("")
    var newEventDesc = mutableStateOf("")
    var showAddEventDialog = mutableStateOf(false)

    fun addEvent() {
        if (newEventTitle.value.isNotBlank()) {
            val newId = (events.value.maxOfOrNull { it.id } ?: 0) + 1
            events.value = events.value + CalendarEvent(
                id = newId,
                title = newEventTitle.value,
                date = LocalDate.now(),
                description = newEventDesc.value
            )
            newEventTitle.value = ""
            newEventDesc.value = ""
            showAddEventDialog.value = false
        }
    }
}

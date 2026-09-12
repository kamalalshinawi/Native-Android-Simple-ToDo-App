package com.example.firstkotlinapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstkotlinapp.data.Habit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScreen() {
    var habits by remember { mutableStateOf(listOf(
        Habit(1, "Drink 2L Water", 5, true),
        Habit(2, "Read 20 pages", 3, false),
        Habit(3, "Morning Run", 10, true)
    )) }
    var newHabitName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Habit Tracker") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newHabitName.isNotBlank()) {
                    val newId = (habits.maxOfOrNull { it.id } ?: 0) + 1
                    habits = habits + Habit(newId, newHabitName)
                    newHabitName = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TextField(
                value = newHabitName,
                onValueChange = { newHabitName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Enter new habit...") }
            )
            LazyColumn {
                items(habits, key = { it.id }) { habit ->
                    HabitItem(
                        habit = habit,
                        onToggle = {
                            habits = habits.map {
                                if (it.id == habit.id) {
                                    val newCompleted = !it.isCompletedToday
                                    it.copy(
                                        isCompletedToday = newCompleted,
                                        streak = if (newCompleted) it.streak + 1 else it.streak - 1
                                    )
                                } else it
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = habit.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Streak: ${habit.streak} days", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = onToggle,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (habit.isCompletedToday) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(Icons.Default.Check, contentDescription = "Complete")
            }
        }
    }
}

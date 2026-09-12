package com.example.firstkotlinapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstkotlinapp.data.CalendarEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var events by remember { mutableStateOf(listOf(
        CalendarEvent(1, "Dentist Appointment", LocalDate.now(), "Routine checkup"),
        CalendarEvent(2, "Project Meeting", LocalDate.now().plusDays(1), "Discuss architecture"),
        CalendarEvent(3, "Birthday Party", LocalDate.now().plusDays(2), "Gift required")
    )) }
    
    var showAddDialog by remember { mutableStateOf(false) }
    var newEventTitle by remember { mutableStateOf("") }
    var newEventDesc by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Calendar") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        }
    ) { padding ->
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Event") },
                text = {
                    Column {
                        TextField(
                            value = newEventTitle,
                            onValueChange = { newEventTitle = it },
                            label = { Text("Event Title") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = newEventDesc,
                            onValueChange = { newEventDesc = it },
                            label = { Text("Description") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (newEventTitle.isNotBlank()) {
                            val newId = (events.maxOfOrNull { it.id } ?: 0) + 1
                            events = events + CalendarEvent(
                                id = newId,
                                title = newEventTitle,
                                date = LocalDate.now(), // Simplified for now
                                description = newEventDesc
                            )
                            newEventTitle = ""
                            newEventDesc = ""
                            showAddDialog = false
                        }
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Simple Month View Placeholder
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            HorizontalDivider()
            
            LazyColumn {
                items(events, key = { it.id }) { event ->
                    EventItem(event = event)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: CalendarEvent) {
    ListItem(
        headlineContent = { Text(event.title) },
        supportingContent = { Text("${event.date.format(DateTimeFormatter.ofPattern("MMM dd"))} - ${event.description}") },
        leadingContent = {
            Icon(Icons.Default.DateRange, contentDescription = null)
        }
    )
}

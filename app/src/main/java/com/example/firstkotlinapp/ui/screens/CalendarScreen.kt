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
import com.example.firstkotlinapp.ui.viewmodel.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: MainViewModel) {
    val events by viewModel.events
    var showAddDialog by viewModel.showAddEventDialog
    var newEventTitle by viewModel.newEventTitle
    var newEventDesc by viewModel.newEventDesc

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Calendar") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Event") },
                text = {
                    Column {
                        TextField(
                            value = newEventTitle,
                            onValueChange = { viewModel.newEventTitle.value = it },
                            label = { Text("Event Title") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = newEventDesc,
                            onValueChange = { viewModel.newEventDesc.value = it },
                            label = { Text("Description") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { viewModel.addEvent() }) {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        ListItem(
            headlineContent = { Text(event.title) },
            supportingContent = { Text("${event.date.format(DateTimeFormatter.ofPattern("MMM dd"))} - ${event.description}") },
            leadingContent = {
                Icon(Icons.Default.DateRange, contentDescription = null)
            },
            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
        )
    }
}

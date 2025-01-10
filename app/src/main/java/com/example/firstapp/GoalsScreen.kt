package com.example.firstapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Task(
    var title: String,
    val date: String,
    var isCompleted: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GoalsScreen() {
    var tasks = remember { mutableStateListOf<Task>() }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Daily Goals", modifier = Modifier.padding(bottom = 16.dp))
                LazyColumn {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onLongClick = {
                                editingTask = task
                                newTaskTitle = task.title
                                showEditDialog = true
                            }
                        )
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Create New Goal") },
            text = {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("Goal Title") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    tasks.add(Task(newTaskTitle, currentDate))
                    newTaskTitle = ""
                    showCreateDialog = false
                }) {
                    Text("Create")
                }
            },
            dismissButton = {
                Button(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEditDialog && editingTask != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Goal") },
            text = {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("Goal Title") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    editingTask?.title = newTaskTitle
                    newTaskTitle = ""
                    showEditDialog = false
                    editingTask = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showEditDialog = false
                    editingTask = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(task: Task, onLongClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .combinedClickable(
                onClick = { },
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isChecked by remember { mutableStateOf(task.isCompleted) }
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                task.isCompleted = it
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = task.title)
            Text(text = task.date, color = Color.Gray)
        }
    }
}
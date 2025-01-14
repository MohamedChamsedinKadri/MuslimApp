package com.example.firstapp.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstapp.R
import com.example.firstapp.ui.goals.components.AddGoalButton
import com.example.firstapp.ui.goals.components.GoalItem

data class Goal(val id: Int, val name: String, val description: String, val isCompleted: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    goals: List<Goal> = listOf(
        Goal(1, "Read Quran", "Read 1 Juz of Quran", false),
        Goal(2, "Pray", "Pray 5 times a day", true),
        Goal(3, "Learn", "Learn 1 new dua", false),
        Goal(4, "Charity", "Give to charity", false),
        Goal(5, "Fast", "Fast on Mondays and Thursdays", true),
        Goal(6, "Reflect", "Reflect on the meaning of the Quran", false),
        Goal(7, "Memorize", "Memorize 1 new surah", false),
        Goal(8, "Attend", "Attend a religious lecture", false),
        Goal(9, "Help", "Help someone in need", true),
        Goal(10, "Share", "Share a religious message", false)
    ),
    onAddGoalClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.goals_title),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            AddGoalButton(onClick = onAddGoalClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(goals) { goal ->
                    GoalItem(goal = goal)
                }
            }
        }
    }
}
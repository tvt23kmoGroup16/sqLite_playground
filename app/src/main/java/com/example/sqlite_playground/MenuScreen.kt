package com.example.sqlite_playground

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "SQLite Playground",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select a menu option",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate("add_user_screen") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Add User")
                }
                Button(
                    onClick = { navController.navigate("update_user_screen") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Update User")
                }
                Button(
                    onClick = { navController.navigate("delete_user_screen") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Delete User")
                }
                Button(
                    onClick = { navController.navigate("view_all_users_screen") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("View All Users")
                }
            }
        }
    }
}

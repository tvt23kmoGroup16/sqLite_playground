package com.example.sqlite_playground

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sqlite_playground.db.DatabaseHelper
import com.example.sqlite_playground.db.models.User
import com.example.sqlite_playground.db.repositories.UserRepository
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun ViewAllUsersScreen(navController: NavHostController) {
    var users by remember { mutableStateOf(listOf<User>()) }
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    val userRepository = UserRepository(dbHelper)

    LaunchedEffect(Unit) {
        users = userRepository.getAllUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Users",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        if (users.isEmpty()) {
            Text(
                text = "No users found.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
        } else {
            LazyColumn {
                items(users) { user ->
                    UserItem(user = user)
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Name: ${user.username}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

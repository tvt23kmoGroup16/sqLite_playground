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
        Text(text = "All Users", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        if (users.isEmpty()) {
            Text(text = "No users found.")
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "ID: ${user.id}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Name: ${user.username}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
    }
}

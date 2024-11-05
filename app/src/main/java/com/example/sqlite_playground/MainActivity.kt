package com.example.sqlite_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import com.example.sqlite_playground.db.DatabaseHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
/*    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var users by remember { mutableStateOf(listOf<User>()) }

    val dbHelper = DatabaseHelper(LocalContext.current)

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Button(onClick = {
            if (name.isNotEmpty() && email.isNotEmpty()) {
                dbHelper.addUser(name, email)
                name = ""
                email = ""
                users = dbHelper.getAllUsers() // Refreshing list of users
            }
        }) {
            Text("Add User")
        }

        // Display all users when called
        Text(text = "Users:")
        users.forEach { user ->
            Text(text = "${user.name} - ${user.email}")
        }
  }*/
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
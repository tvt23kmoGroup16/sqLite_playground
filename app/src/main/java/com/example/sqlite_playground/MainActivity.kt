package com.example.sqlite_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    val dbHelper = DatabaseHelper(LocalContext.current)
    var users by remember { mutableStateOf(listOf("UserTable")) }

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
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )


        Button(onClick = {
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val lastlogin = "LocalDateTime.now()" //<----------Attempting to save the latest login when creating user? Better ideas how to do this?
                dbHelper.addUser(name, email, password, lastlogin)
                name = ""
                email = ""
                password = ""
                users = dbHelper.getAllUsers() // Refreshing list of users
            }
        }) {
            Text("User Added Successfully!") //Gz
        }

        //Display all users when called
        Text(text = "Users:")
        users.forEach { _ -> Text(text = "$name - $email") }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
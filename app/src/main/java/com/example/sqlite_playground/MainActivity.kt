package com.example.sqlite_playground

import android.os.Bundle
import android.widget.Toast
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
import com.example.sqlite_playground.db.models.User
import com.example.sqlite_playground.db.repositories.UserRepository


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
    val userRepository = UserRepository(dbHelper)
    var users by remember { mutableStateOf(listOf<User>()) } // A list of User objects, not strings
    val context = LocalContext.current


    // Function to refresh user list
    fun refreshUserList() {
        users = userRepository.getAllUsers() // Fetch users
    }

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
                val lastLogin = System.currentTimeMillis() // using System.currentTimeMillis for the TimeStamp

               val userId = userRepository.insertUser(name, email, password, lastLogin)

                if(userId > 0) {
                name = ""
                email = ""
                password = ""
                refreshUserList()
                users = userRepository.getAllUsers() // Refreshing list of users
                Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error adding user.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Add User")
        }

        //Display all users when called
        Text(text = "Users:")
        users.forEach { user ->
            Text(text = "${user.username} - ${user.email}")  //Displaying user details, let's call username and email with their parent
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
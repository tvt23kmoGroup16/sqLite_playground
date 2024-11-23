package com.example.sqlite_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "menu_screen") {
                composable("menu_screen") { MenuScreen(navController) }
                composable("add_user_screen") { AddUserScreen(navController) }
                composable("update_user_screen") { UpdateUserScreen(navController) }
                composable("delete_user_screen") { DeleteUserScreen(navController) }
                composable("view_all_users_screen") { ViewAllUsersScreen(navController) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    MenuScreen(navController)
}


package com.example.projektmarcinfedorowicz

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object ListScreen : Screen("list_screen")
    object SettingsScreen : Screen("settings_screen")
    object AddScreen : Screen("add_screen")
    object DetailsScreen : Screen("details_screen")
    object EditScreen : Screen("edit_screen")
}
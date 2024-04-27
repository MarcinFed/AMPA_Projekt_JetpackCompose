package com.example.projektmarcinfedorowicz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projektmarcinfedorowicz.home.HomeScreen
import com.example.projektmarcinfedorowicz.Screen
import com.example.projektmarcinfedorowicz.settings.Settings
import com.example.projektmarcinfedorowicz.database.MyViewModel
import com.example.projektmarcinfedorowicz.list.AddProduct
import com.example.projektmarcinfedorowicz.list.DetailsScreen
import com.example.projektmarcinfedorowicz.list.EditProduct

@Composable
fun Navigation(myViewModel: MyViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController)
        }
        composable(route = Screen.ListScreen.route){
            com.example.projektmarcinfedorowicz.list.List(navController, myViewModel)
        }
        composable(route = Screen.SettingsScreen.route){
            Settings(navController)
        }
        composable(route = Screen.AddScreen.route){
            AddProduct(navController, myViewModel)
        }
        composable(route = Screen.DetailsScreen.route + "/{id}"){
            val productId = it.arguments?.getString("id")
            DetailsScreen(productId = productId, navController = navController, myViewModel = myViewModel)
        }
        composable(route = Screen.EditScreen.route + "/{id}"){
            val productId = it.arguments?.getString("id")
            EditProduct(productId = productId, navController = navController, myViewModel = myViewModel)
        }
    }
}
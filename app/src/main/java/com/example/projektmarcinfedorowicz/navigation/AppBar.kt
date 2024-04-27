package com.example.projektmarcinfedorowicz.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.projektmarcinfedorowicz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationItemClick: () -> Unit,
    title: String
) {
    TopAppBar(
        title = {
            Text(text = title, color = Color.White)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.basic_yellow)),
        navigationIcon = {
            IconButton(onClick = onNavigationItemClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer", tint = Color.White)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarNoDrawer(
    onNavigationItemClick: () -> Unit,
    title: String
){
    TopAppBar(
        title = {
            Text(text = title, color = Color.White)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.basic_yellow)),
        navigationIcon = {
            IconButton(onClick = onNavigationItemClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back", tint = Color.White)
            }
        }
    )
}
package com.example.projektmarcinfedorowicz.home

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.MenuItem
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.Screen
import com.example.projektmarcinfedorowicz.navigation.AppBar
import com.example.projektmarcinfedorowicz.navigation.DrawerBody
import com.example.projektmarcinfedorowicz.navigation.DrawerHeader
import com.example.projektmarcinfedorowicz.settings.AppPreferences
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var drawerState2 by remember { mutableStateOf(DrawerState(initialValue = DrawerValue.Closed)) }
    var appPreferences = AppPreferences(LocalContext.current.applicationContext as Application)

    ModalNavigationDrawer(
        drawerState = drawerState2,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = listOf(
                    MenuItem(
                        id = "mainscreen",
                        title = "Main screen",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(id = "list", title = "Shopping list", icon = Icons.Default.List),
                    MenuItem(id = "options", title = "Settings", icon = Icons.Default.Settings)
                ), onItemClick = {
                    drawerState2 = DrawerState(initialValue = DrawerValue.Closed)
                    when (it.id) {
                        "list" -> navController.navigate(Screen.ListScreen.route)
                        "options" -> navController.navigate(Screen.SettingsScreen.route)
                    }
                    println("Clicked on ${it.title}")
                })
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppBar(onNavigationItemClick = {
                    scope.launch {
                        drawerState2.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }, "Main Screen")
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "${appPreferences.invitationText}",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 36.sp
                        )
                        Image(
                            painter = painterResource(
                                id = when (appPreferences.profilePicture) {
                                    0 -> R.drawable.profile_picture_1
                                    1 -> R.drawable.profile_picture_2
                                    2 -> R.drawable.profile_picture_3
                                    3 -> R.drawable.profile_picture_4
                                    else -> R.drawable.profile_picture_1
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp)
                        )
                        Text(
                            text = "${appPreferences.nickname}",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }
    }
}
package com.example.projektmarcinfedorowicz.settings

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.MenuItem
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.Screen
import com.example.projektmarcinfedorowicz.navigation.AppBar
import com.example.projektmarcinfedorowicz.navigation.DrawerBody
import com.example.projektmarcinfedorowicz.navigation.DrawerHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Settings(navController: NavController){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var drawerState2 by remember { mutableStateOf(DrawerState(initialValue = DrawerValue.Closed)) }
    val scope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }
    var appPreferences = AppPreferences(LocalContext.current.applicationContext as Application)
    var context = LocalContext.current

    ModalNavigationDrawer (
        drawerState = drawerState2,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = listOf(
                    MenuItem(id = "mainscreen", title = "Main screen", icon = Icons.Default.Home),
                    MenuItem(id = "list", title = "Shopping list", icon = Icons.Default.List),
                    MenuItem(id = "options", title = "Settings", icon =  Icons.Default.Settings)
                ), onItemClick = {
                    drawerState2 = DrawerState(initialValue = DrawerValue.Closed)
                    when(it.id){
                        "mainscreen" -> navController.navigate(Screen.MainScreen.route)
                        "list" -> navController.navigate(Screen.ListScreen.route)
                    }
                    println("Clicked on ${it.title}")
                })
            }
        }
    ){
        Scaffold(topBar = {AppBar(onNavigationItemClick = {
                    scope.launch {
                        drawerState2.apply {
                            if(isClosed) open() else close()
                        }
                    }
                }, "Settings")}
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SettingsTabs(selectedTabIndex = selectedTabIndex, updateTabIndex = {newTabIndex ->
                    selectedTabIndex = newTabIndex
                })

                when(selectedTabIndex){
                    0 -> {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var firstLine by remember { mutableStateOf(appPreferences.getFirstLine()) }
                                Text(text = "Invitation text", fontSize = 28.sp, modifier = Modifier.padding(8.dp))
                                TextField(
                                    value = firstLine,
                                    onValueChange = {firstLine = it },
                                    placeholder = { Text("Set custom invitation") },
                                    modifier = Modifier.width(200.dp)
                                )

                            var secondLine by remember { mutableStateOf(appPreferences.getSecondLine()) }
                                Text(text = "Nickname", fontSize = 28.sp, modifier = Modifier.padding(8.dp))
                                TextField(
                                    value = secondLine,
                                    onValueChange = {secondLine = it },
                                    placeholder = { Text("Set your nickname") },
                                    modifier = Modifier.width(200.dp)
                                )

                            Spacer(modifier = Modifier.weight(1f))
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 60.dp)
                            ){
                                Button(
                                    onClick = {
                                        appPreferences.reset()
                                        firstLine = ""
                                        secondLine = ""
                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Text(text = "Reset", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                                }
                                Button(
                                    onClick = {
                                        appPreferences.invitationText = firstLine
                                        appPreferences.nickname = secondLine
                                        Toast.makeText(context, "Invitation and nickname changed", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ){
                                    Text(text = "Save", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                                }

                            }
                        }
                    }
                    1 -> {
                        val pagerState = rememberPagerState(pageCount = {
                            4
                        })
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            VerticalPager(
                                state = pagerState,
                                modifier = Modifier.size(650.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,

                            ) { page ->
                                when(page) {
                                    0 -> {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_picture_1),
                                                contentDescription = null,
                                                modifier = Modifier.size(300.dp)
                                            )
                                        }
                                    }
                                    1 -> {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_picture_2),
                                                contentDescription = null,
                                                modifier = Modifier.size(300.dp)
                                            )
                                        }
                                    }
                                    2 -> {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_picture_3),
                                                contentDescription = null,
                                                modifier = Modifier.size(300.dp)
                                            )
                                        }
                                    }
                                    3 -> {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_picture_4),
                                                contentDescription = null,
                                                modifier = Modifier.size(300.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 60.dp)
                            ){
                                Button(
                                    onClick = {
                                        appPreferences.resetPicture()
                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Text(text = "Reset", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                                }
                                Button(
                                    onClick = {
                                        appPreferences.profilePicture = pagerState.currentPage
                                        Log.println(Log.DEBUG, "Settings", "Profile picture changed to ${pagerState.currentPage}")
                                        Toast.makeText(context, "Profile picture changed", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ){
                                    Text(text = "Save", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                                }

                            }
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun SettingsTabs(selectedTabIndex: Int, updateTabIndex: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.height(50.dp), contentColor = Color.White)
    {
        Tab(selected = selectedTabIndex == 0, onClick = {updateTabIndex(0)}) {
            Text("Invitation", modifier = Modifier.padding(10.dp))
        }
        Tab(selected = selectedTabIndex == 1, onClick = {updateTabIndex(1)}) {
            Text("Profile picture", modifier = Modifier.padding(10.dp))
        }
    }
}

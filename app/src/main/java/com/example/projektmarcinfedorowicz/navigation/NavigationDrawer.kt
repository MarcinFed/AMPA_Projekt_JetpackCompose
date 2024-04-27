package com.example.projektmarcinfedorowicz.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.MenuItem
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.Screen
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(colorResource(id = R.color.basic_yellow)),
        contentAlignment = Alignment.Center,

        ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(text = "ShopList", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White))
            Image(painter = painterResource(id = R.drawable.icons8_fast_cart_90), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(10.dp))
        }

    }
}

@Composable
fun DrawerBody(
    items:List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 22.sp),
    onItemClick: (MenuItem) -> Unit
){
    LazyColumn(modifier){
        items(items){ item ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ){
                Icon(imageVector = item.icon, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DrawerNav(navController : NavController){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var appbarTitle by remember{
        mutableStateOf("Main screen")
    }
    ModalNavigationDrawer (
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = listOf(
                    MenuItem(id = "mainscreen", title = "Main screen", icon = Icons.Default.Home),
                    MenuItem(id = "list", title = "Pokemon list", icon = Icons.Default.List),
                    MenuItem(id = "options", title = "Options", icon =  Icons.Default.Settings)
                ), onItemClick = {
                    when(it.id){
                        "mainscreen" -> navController.navigate(Screen.MainScreen.route)
                        "list" -> navController.navigate(Screen.ListScreen.route)
                        "options" -> navController.navigate(Screen.SettingsScreen.route)
                    }
                    appbarTitle = it.title
                    println("Clicked on ${it.title}")
                })
            }
        }
    ){
        Column {
            AppBar(onNavigationItemClick = {
                scope.launch {
                    drawerState.apply {
                        if(isClosed) open() else close()
                    }
                }
            }, appbarTitle)
        }
    }
}
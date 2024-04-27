package com.example.projektmarcinfedorowicz.list

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.navigation.AppBar
import com.example.projektmarcinfedorowicz.navigation.DrawerBody
import com.example.projektmarcinfedorowicz.navigation.DrawerHeader
import com.example.projektmarcinfedorowicz.MenuItem
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.Screen
import com.example.projektmarcinfedorowicz.database.DBItem
import com.example.projektmarcinfedorowicz.database.MyViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun List(navController: NavController, myViewModel: MyViewModel){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var drawerState2 by remember { mutableStateOf(DrawerState(initialValue = DrawerValue.Closed)) }
    val scope = rememberCoroutineScope()

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
                }, "Shopping List")
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddScreen.route)
                        //set the color of the floating action button to my basic_yellow color
                    },
                    containerColor = colorResource(id = R.color.basic_yellow),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, "Add product")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text ="Shopping List", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    val itemsState =
                        myViewModel.getAllItems()?.collectAsState(initial = emptyList())
                    LazyColumn(Modifier.fillMaxSize()) {
                        itemsState!!.value.forEach { item ->
                            item {
                                ListItem(item, Modifier, myViewModel, navController)
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(data: DBItem, modifier: Modifier = Modifier, myViewModel: MyViewModel, navController: NavController){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .combinedClickable(
                onClick = {
                    navController.navigate(Screen.DetailsScreen.route + "/${data.id}")
                },
                onLongClick = {
                    showDialog = true
                }
            )
    ){
        val imgId = when(data.ProductType){
            1 -> R.drawable.icons8_grain_96
            2 -> R.drawable.icons8_vegetables_96
            3 -> R.drawable.icons8_fruits_64
            4 -> R.drawable.icons8_milk_96
            5 -> R.drawable.icons8_meat_100
            6 -> R.drawable.icons8_candy_96
            else -> R.drawable.icons8_other_100
        }
        Image(modifier = Modifier
            .size(80.dp)
            .padding(10.dp), painter = painterResource(id = imgId), contentDescription = null)
        Column (
            Modifier.align(Alignment.CenterVertically)
        ){
            Text(text = data.Product, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Amount: ${data.Amount}", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.basic_yellow)
            ),
            checked = data.Bought,
            onCheckedChange = {
                myViewModel.modifyItem(data.id, data.Product, data.Use, data.Amount, data.ProductType, data.Available, !data.Bought)
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp)
        )
        if (showDialog) {
            DeleteConfirmationDialog(
                onConfirm = {
                    myViewModel.deleteItem(data)
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }

    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Confirmation")
        },
        text = {
            Text("Are you sure to delete this product from the shopping list?")
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        }
    )
}

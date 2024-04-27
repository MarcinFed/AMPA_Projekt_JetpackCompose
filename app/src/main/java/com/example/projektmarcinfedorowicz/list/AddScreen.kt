package com.example.projektmarcinfedorowicz.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.navigation.AppBarNoDrawer
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.database.DBItem
import com.example.projektmarcinfedorowicz.database.MyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddProduct(navController: NavController, myViewModel: MyViewModel) {
    Scaffold(
        topBar = {
            AppBarNoDrawer(onNavigationItemClick = {
                navController.popBackStack()
            }, "Add Product")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            var product by remember { mutableStateOf("") }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Product name", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                TextField(
                    value = product,
                    onValueChange = {product = it },
                    placeholder = { Text("Product name") },
                    modifier = Modifier.width(200.dp)
                )
            }

            var use by remember { mutableStateOf("") }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "What is it for", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                TextField(
                    value = use,
                    onValueChange = {use = it },
                    placeholder = { Text("What is it for") },
                    modifier = Modifier.width(200.dp)
                )
            }

            val values = remember { (1..99).map { it.toString() } }
            val valuesPickerState = rememberPickerState()
            var quantity by remember { mutableIntStateOf(1) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Amount", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                    Picker(
                        state = valuesPickerState,
                        items = values,
                        onValueChange = { quantity = it.toInt() },
                        visibleItemsCount = 3,
                        textModifier = Modifier.padding(8.dp),
                        modifier = Modifier.width(75.dp)
                    )
                }
            }

            var productType by remember { mutableIntStateOf(1) }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Product type", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                    ProductTypeSpinner(onItemSelected = { productType = it.third
                    Log.d("Debug", "Product type: $productType")})
                }
            }


            var available by remember { mutableStateOf(false) }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Available", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                Checkbox(
                    checked = available,
                    onCheckedChange = {available = it},
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.basic_yellow)
                    )

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
            ){
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    //make text bold
                    Text(text = "Cancel", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                }
                Button(
                    onClick = {
                        val newItem = DBItem(
                            product,
                            use,
                            quantity,
                            productType,
                            available
                        )
                        myViewModel.insertItem(newItem)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                    modifier = Modifier.padding(bottom = 8.dp)
                ){
                    Text(text = "Add", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                }

            }
        }
    }

}


@Composable
fun ProductTypeSpinner(onItemSelected: (Triple<String, Int, Int>) -> Unit , option: Int = 0){
    val options = listOf(
        Triple("Grain", R.drawable.icons8_grain_96, 1),
        Triple("Vegetables", R.drawable.icons8_vegetables_96,2),
        Triple("Fruits", R.drawable.icons8_fruits_64, 3),
        Triple("Dairy", R.drawable.icons8_milk_96, 4),
        Triple("Meat", R.drawable.icons8_meat_100, 5),
        Triple("Candy", R.drawable.icons8_candy_96, 6),
        Triple("Other", R.drawable.icons8_other_100, 7)
    )
    var expandedState by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[option]) }

    Row(
        modifier = Modifier
            .clickable {
                expandedState = !expandedState
            }
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ){
        Image(painter = painterResource(id = selectedOption.second), contentDescription = null)
        Text(text = selectedOption.first)
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)

        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = {
                expandedState = false
            }
        ) {
            for (option in options) {
                DropdownMenuItem(
                    text = {
                        SpinnerOption(imgId = option.second, name = option.first)
                    },
                    onClick = {
                        selectedOption = option
                        expandedState = false
                        onItemSelected(option)
                    })
            }
        }
    }

}

@Composable
fun SpinnerOption(
    imgId: Int,
    name: String
){
    Row (
        modifier = Modifier
            .height(50.dp)
            .width(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Image(painter = painterResource(id = imgId), contentDescription = "product")
        Text(text = name)
    }
}
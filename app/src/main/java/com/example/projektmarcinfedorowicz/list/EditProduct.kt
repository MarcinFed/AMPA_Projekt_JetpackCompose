package com.example.projektmarcinfedorowicz.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.navigation.AppBarNoDrawer
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.database.MyViewModel

@Composable
fun EditProduct(productId: String?, navController: NavController, myViewModel: MyViewModel) {

    val productEdit = myViewModel.getItem(productId!!.toInt())

    Scaffold(
        topBar = {
            AppBarNoDrawer(onNavigationItemClick = {
                navController.popBackStack()
            }, "Edit Product")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            var product by remember { mutableStateOf(productEdit!!.Product) }
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

            var use by remember { mutableStateOf(productEdit!!.Use) }
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
            var quantity by remember { mutableIntStateOf(productEdit!!.Amount) }
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
                        startIndex = quantity - 1,
                        onValueChange = { quantity = it.toInt() },
                        visibleItemsCount = 3,
                        textModifier = Modifier.padding(8.dp),
                        modifier = Modifier.width(75.dp)
                    )
                }
            }

            var productType by remember { mutableIntStateOf(productEdit!!.ProductType) }
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
                        Log.d("Debug", "Product type: $productType")},
                        option = productType-1)
                }
            }


            var available by remember { mutableStateOf(productEdit!!.Available) }
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
                        myViewModel.modifyItem(productEdit!!.id, product, use, quantity, productType, available, productEdit!!.Bought)
                        navController.popBackStack()
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
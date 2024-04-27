package com.example.projektmarcinfedorowicz.list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projektmarcinfedorowicz.navigation.AppBarNoDrawer
import com.example.projektmarcinfedorowicz.R
import com.example.projektmarcinfedorowicz.Screen
import com.example.projektmarcinfedorowicz.database.MyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(productId: String?, navController: NavController, myViewModel: MyViewModel) {

    val productDetails = myViewModel.getItem(productId!!.toInt())

    Scaffold(
        topBar = {
            AppBarNoDrawer(onNavigationItemClick = {
                navController.popBackStack()
            }, "Product Details")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = productDetails?.Product ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                // Obrazek reprezentujący typ produktu
                val imgId = when (productDetails?.ProductType) {
                    1 -> R.drawable.icons8_grain_96
                    2 -> R.drawable.icons8_vegetables_96
                    3 -> R.drawable.icons8_fruits_64
                    4 -> R.drawable.icons8_milk_96
                    5 -> R.drawable.icons8_meat_100
                    6 -> R.drawable.icons8_candy_96
                    else -> R.drawable.icons8_other_100
                }
                val type = when (productDetails?.ProductType) {
                    1 -> "Grain"
                    2 -> "Vegetables"
                    3 -> "Fruits"
                    4 -> "Milk"
                    5 -> "Meat"
                    6 -> "Candy"
                    else -> "Other"
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$type",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                        Image(
                            painter = painterResource(id = imgId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .padding(10.dp)
                        )
                    }
                }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = "Needed for ${productDetails?.Use ?: ""}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = "Buy ${productDetails?.Amount}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = if (productDetails?.Available == true) "It was available" else "It wasn't available",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = if (productDetails?.Bought == true) "Bought" else "Not bought yet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(top = 175.dp)
                ){
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(text = "Back", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.EditScreen.route + "/${productDetails?.id}")
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.basic_yellow)),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ){
                        Text(text = "Edit", fontSize = 20.sp, fontStyle = FontStyle.Normal)
                    }

                }
            }
        }

    }
}

package com.example.projektmarcinfedorowicz

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projektmarcinfedorowicz.database.MyViewModel
import com.example.projektmarcinfedorowicz.database.MyViewModelFactory
import com.example.projektmarcinfedorowicz.navigation.Navigation
import com.example.projektmarcinfedorowicz.ui.theme.ProjektMarcinFedorowiczTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            window.statusBarColor = getColor(R.color.basic_yellow)
            ProjektMarcinFedorowiczTheme {
                val myViewModel: MyViewModel = viewModel(
                    factory = MyViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Navigation(myViewModel)
            }
        }
    }
}
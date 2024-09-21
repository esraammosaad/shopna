package com.example.shopna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.example.shopna.presentation.view.home.MainScreen
import com.example.shopna.ui.theme.ShopnaTheme
import com.example.shopna.presentation.view.landing.OnBoardingScreen
import com.example.shopna.presentation.view_model.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopnaTheme {

              //  Navigator(screen = OnBoardingScreen())


                val viewModel=MainViewModel()
                // HomeScreen(MainViewModel()
                Navigator(MainScreen(viewModel))


                }
            }

        }
    }


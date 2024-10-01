package com.example.shopna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.shopna.presentation.view.authentication.LoginScreen
import com.example.shopna.presentation.view.home.MainScreen
import com.example.shopna.presentation.view.landing.OnBoardingScreen
import com.example.shopna.presentation.view_model.AuthViewModel
import com.example.shopna.presentation.view_model.HomeViewModel
import com.example.shopna.ui.theme.ShopnaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            ShopnaTheme {

                  // if(getAuthToken()==null) Navigator(OnBoardingScreen()) else Navigator(MainScreen(HomeViewModel()))

               if(isOnBoardingShown()) Navigator(LoginScreen(this))else Navigator(OnBoardingScreen())







            }
        }
    }





    private fun isOnBoardingShown(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("on_boarding_shown", false)
    }
}

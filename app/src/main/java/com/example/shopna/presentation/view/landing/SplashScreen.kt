package com.example.shopna.presentation.view.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopna.ui.theme.kPrimaryColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        visible = true
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = androidx.compose.animation.expandVertically(animationSpec = tween(durationMillis = 1000)),
            exit = androidx.compose.animation.fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Text(
                text = "Shopna.",
                style = TextStyle(
                    fontSize = 45.sp,
                    color = kPrimaryColor
                ),
                modifier = Modifier.padding(bottom = 100.dp).align(Alignment.Center)
            )
        }
    }
}

package com.example.shopna.presentation.view.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Text(text = "Profile", modifier = Modifier.padding(start = 12.dp, top = 15.dp)
        , style = TextStyle(fontSize = 25.sp)
    )



}
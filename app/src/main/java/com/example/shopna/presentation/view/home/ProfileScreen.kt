package com.example.shopna.presentation.view.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopna.presentation.view_model.AuthViewModel

@Composable
fun ProfileScreen() {
    Text(text = "Profile", modifier = Modifier.padding(start = 12.dp, top = 15.dp)
        , style = TextStyle(fontSize = 25.sp)
    )



}
/*@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {
    val userState = authViewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        userState.value?.data?.image?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = userState.value?.data?.name ?: "User Name",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // User Details (email, phone, etc.)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
          //  elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Email: ${userState.value?.data?.email ?: "N/A"}", style = TextStyle(fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Phone: ${userState.value?.data?.phone ?: "N/A"}", style = TextStyle(fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Credit: ${userState.value?.data?.credit ?: 0}", style = TextStyle(fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Points: ${userState.value?.data?.points ?: 0}", style = TextStyle(fontSize = 18.sp))
            }
        }
    }
}*/

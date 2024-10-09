package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopna.R
import com.example.shopna.data.model.GetUserResponse
import com.example.shopna.ui.theme.backgroundColor
import com.example.shopna.ui.theme.kPrimaryColor
import com.example.shopna.ui.theme.lightGreyColor
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(user: StateFlow<GetUserResponse?>) {
    val userData = user.collectAsState()
    var isClicked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(170.dp)){

        Box ( modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(kPrimaryColor)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Profile",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interregular)),
                        color = Color.White
                    )
                )

                CustomIcon(
                    icon = painterResource(id = R.drawable.magnifyingglass),
                    onClick = { }
                )
            }
        }
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),horizontalAlignment = Alignment.CenterHorizontally
                ) {

                Spacer(modifier = Modifier.height(16.dp))
                userData.value?.data?.image?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape)
                            .border(5.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }
            }}


        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = userData.value?.data?.name ?: "User Name",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column {
                OptionRow(
                    optionText = "Dark Mode",
                    onClick = {
                        isClicked.value=!isClicked.value
                    },
                    isToggle = true,
                    isChecked = isClicked.value
                )
                Spacer(modifier = Modifier.height(5.dp))


                OptionRow(
                    optionText = "Edit Profile",
                    onClick = {  }
                )
                Spacer(modifier = Modifier.height(5.dp))


                OptionRow(
                    optionText = "Orders",
                    onClick = { }
                )
                Spacer(modifier = Modifier.height(5.dp))


                OptionRow(
                    optionText = "Addresses",
                    onClick = { }
                )
                Spacer(modifier = Modifier.height(5.dp))


                OptionRow(
                    optionText = "Language",
                    onClick = {  }
                )
                Spacer(modifier = Modifier.height(5.dp))




                OptionRow(
                    optionText = "Logout",
                    onClick = { }
                )
            }

        }
    }
}


@Composable
fun OptionRow(
    optionText: String,
    onClick: () -> Unit,
    isToggle: Boolean = false,
    isChecked: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = optionText,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)
        )
        if (isToggle) {
            Switch(checked = isChecked, onCheckedChange = { onClick() }, colors = SwitchDefaults.colors(checkedIconColor = kPrimaryColor, checkedBorderColor = lightGreyColor, checkedTrackColor = kPrimaryColor ))
        } else {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}


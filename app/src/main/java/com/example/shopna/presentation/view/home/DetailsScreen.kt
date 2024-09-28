package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.shopna.R
import com.example.shopna.data.model.Products
import com.example.shopna.ui.theme.backgroundColor
import com.example.shopna.ui.theme.kPrimaryColor


class DetailsScreen(private val product: Products) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 60.dp)
            ) {
                IconButton(
                    onClick = {
                        navigator.pop()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Home",
                        tint = Color.Black
                    )
                }
                if (product.images.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                            .height(250.dp)
                    ) {
                        items(product.images) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(300.dp)
                                    .padding(end = 1.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                } else {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row( horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    val productName = product.name ?: ""
                    val words = productName.split(" ")
                    Text(
                       // text = product.name ?: "",
                        text = if (words.size > 6) words.take(4).joinToString(" ") + "..." else productName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.Bold,
                        ),
                    )

                    Text(
                        text = "$${product.price} EGP",
                        style = TextStyle(
                            color = kPrimaryColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.W400,
                        ),
                    )
                }

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )

                Text(
                    text = product.description ?: "No description available",
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.interregular)),

                    ),

                )
            }
            Button(
                onClick = { /* Handle Buy Now action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFa477a6) ,
                    contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 16.dp)

            ) {
                Text(text = "Add To Card", overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    //textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.interregular)),


                )


            }
        }
    }
}
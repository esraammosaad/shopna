package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.shopna.R
import com.example.shopna.data.model.DataXXXXX
import com.example.shopna.presentation.view_model.FavoriteViewModel
import com.example.shopna.presentation.view_model.HomeViewModel
import com.example.shopna.ui.theme.backgroundColor
import com.example.shopna.ui.theme.kPrimaryColor

@Composable
fun FavoriteScreen(favoriteViewModel: FavoriteViewModel){
         favoriteViewModel.fetchFavorites()
         val favoriteProducts by favoriteViewModel.favoriteData.collectAsState()
        Column {

                Text(text = "Favorite", modifier = Modifier.padding(start = 12.dp, top = 15.dp)
                    , style = TextStyle(fontSize = 25.sp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    contentPadding = PaddingValues(16.dp)

                ) {

                    favoriteProducts?.data?.data?.let {
                        items(it) { favoriteProduct ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .shadow(4.dp, RoundedCornerShape(8.dp))
                                    .clip(RoundedCornerShape(8.dp)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)


                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    AsyncImage(
                                        model = favoriteProduct.product.image,
                                        contentDescription = favoriteProduct.product.description,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.Gray)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        val productName = favoriteProduct.product.name ?: ""
                                        val words = productName.split(" ")
                                        Text(
                                            text = if (words.size > 6) words.take(4).joinToString(" ") + "..." else productName,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = TextStyle(
                                                fontSize = 15.sp,
                                                fontFamily = FontFamily(Font(R.font.interregular)),
                                                fontWeight = FontWeight.Bold,
                                            ),
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = "$${favoriteProduct.product.price} EGP",
                                            style = TextStyle(
                                                color = kPrimaryColor,
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily(Font(R.font.interregular)),
                                                fontWeight = FontWeight.W400,
                                            ),
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(20.dp))
                                    IconButton(onClick = {

                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "Remove from favorites",
                                            tint = kPrimaryColor

                                        )
                                    }

                                }


                            }
                    }

                }


        }}
}



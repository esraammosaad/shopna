package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.shopna.data.model.FavoriteProducts
import com.example.shopna.presentation.view_model.HomeViewModel

@Composable
fun FavoriteScreen(mainViewModel: HomeViewModel){
    val favorites = mainViewModel.favoritesList.collectAsState()
        // val favorites by mainViewModel.fetchFavorites().collectAsState(initial = emptyList()) // تأكد من تعيين قيمة ابتدائية
        Column {

                Text(text = "Favorite", modifier = Modifier.padding(start = 12.dp, top = 15.dp)
                    , style = TextStyle(fontSize = 25.sp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp),
                    contentPadding = PaddingValues(16.dp)

                ) {

                    items(favorites.value) { favoriteProduct ->
                        FavoriteItem(favoriteProduct,mainViewModel)
                    }

                }


        }

}
@Composable
fun FavoriteItem(favoriteProduct: FavoriteProducts, mainViewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = favoriteProduct.image,
                contentDescription = favoriteProduct.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favoriteProduct.name ?: "",
                    //style = MaterialTheme.typography.h6
                )
                Text(
                    text = "$${favoriteProduct.price} EGP",
                    // style = MaterialTheme.typography.body,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = {
                favoriteProduct.id?.let { id ->
                    mainViewModel.removeFavorite(id)
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Remove from favorites",
                    tint = Color.Magenta

                )
            }

        }


    }
}
package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.shopna.data.model.Banners
import com.example.shopna.data.model.DataCategories
import com.example.shopna.data.model.FavoriteProducts
import com.example.shopna.data.model.FavoriteRequest
import com.example.shopna.data.model.Products
import com.example.shopna.presentation.view_model.MainViewModel




class HomeScreen(val viewModel: MainViewModel) : Screen {
    @Composable
    override fun Content() {
        val homeData = viewModel.products.collectAsState()
        val categories = viewModel.categories.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Banners
            homeData.value?.dataa?.banners?.let { banners ->
                BannerSection(banners)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Categories
            categories.value?.data?.data?.let { categoriesData ->
                CategorySection(categoriesData)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Products Grid
            homeData.value?.dataa?.products?.let { products ->
                ProductGrid(products, viewModel)
            }
        }
    }
}



@Composable
fun BannerSection(banners: List<Banners>) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        items(banners) {
                banner ->
            AsyncImage(model = banner.image, contentDescription ="" , contentScale = ContentScale.Crop,modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 4)
                .width(400.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp)))

        }
    }
}


@Composable
fun CategorySection(categories: List<DataCategories>) {
    Column {
        //Text(text = "Categories", modifier = Modifier.padding(start = 7.dp), fontWeight = FontWeight.Bold, fontSize =20.sp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(categories) { category ->
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = category.image,
                        contentDescription = category.name,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category.name ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}


@Composable
fun ProductGrid(products: List<Products>, viewModel: MainViewModel) {
    Column {
        Text(text = "Special For You", modifier = Modifier.padding(start = 7.dp), fontWeight = FontWeight.Bold, fontSize =20.sp)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(product, viewModel)
            }
        }
    }
}

@Composable
fun ProductItem(product: Products, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
            .wrapContentHeight()
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))

    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.TopEnd
            ) {

                AsyncImage(
                    model = product.image,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                FavoriteIcon(product, viewModel)



            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = product.name ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 4.dp), maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (product.oldPrice != null && product.oldPrice != product.price) {
                    Text(
                        text = "$ ${product.oldPrice} EGP",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
                Row( verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "$ ${product.price} EGP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xfff6C2DC7)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = { /* Handle another action */ },
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = Color.Gray
                            , modifier = Modifier.size(24.dp)
                        )
                    }


                }
            }

        }


    }
}

@Composable
fun FavoriteIcon(product: Products, viewModel: MainViewModel) {
    val isFavorite = viewModel.favoritesList.collectAsState().value.any { it.id == product.id }
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = Color(0xff6C2DC7),
                shape = RoundedCornerShape(
                    topEnd = 20.dp,
                    bottomStart = 10.dp
                )
            )
            .clickable {
                val productId: Int? = product.id
                if (productId != null) {
                    if (isFavorite) {
                        viewModel.removeFavorite(productId)
                    } else {
                        val favoriteProduct = FavoriteProducts(
                            id = product.id,
                            price = product.price,
                            oldPrice = product.oldPrice,
                            discount = product.discount,
                            image = product.image,
                            name = product.name,
                            description = product.description
                        )
                        viewModel.addFavorite(FavoriteRequest(productId = productId), favoriteProduct)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite Icon",
            tint = if (isFavorite) Color.White else Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}
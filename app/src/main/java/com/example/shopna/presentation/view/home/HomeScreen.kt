package com.example.shopna.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopna.data.model.Banners
import com.example.shopna.data.model.DataCategories
import com.example.shopna.data.model.FavoriteProducts
import com.example.shopna.data.model.FavoriteRequest
import com.example.shopna.data.model.Products
import com.example.shopna.presentation.view_model.HomeViewModel
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.shopna.R
import com.example.shopna.ui.theme.greyColor
import com.example.shopna.ui.theme.kPrimaryColor
import com.example.shopna.ui.theme.lightGreyColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val homeData by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        item {
            Column (
                modifier = Modifier
                    .fillMaxSize()

            ){

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = stringResource(id = R.string.welcome_back)+"!", style = TextStyle(
                            fontSize = 18.sp,
                            color = greyColor,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.W200
                        ))
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = "Esraa Mosaad", style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.Bold

                        ))
                    }
                    Row {
                        CustomIcon(icon =painterResource(id = R.drawable.magnifyingglass) , onClick = {})
                        Spacer(modifier = Modifier.width(10.dp))
                        CustomIcon(icon = painterResource(id = R.drawable.notification) , onClick = {})

                    }




                }
                Spacer(modifier = Modifier.height(15.dp))

                Box {
                    Card (modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .wrapContentHeight(),
                        colors = CardDefaults.cardColors(kPrimaryColor)
                    )
                    {

                        Column (  modifier = Modifier.padding(16.dp)){
                            Text(
                                stringResource(id = R.string.new_collection),
                                style = TextStyle(color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.interregular)),
                                    fontWeight = FontWeight.Bold),


                                )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                stringResource(id = R.string.discount_offer),
                                style = TextStyle(color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.interregular)),
                                    fontWeight = FontWeight.W100),
                                lineHeight = 18.sp

                            )
                            Box(modifier = Modifier
                                .wrapContentSize()
                                .padding(vertical = 10.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(Color.White)) {
                                Text(
                                    stringResource(id = R.string.shop_now),modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily =  FontFamily(Font(R.font.interregular))))
                            }


                        }


                    }
                    Box(modifier = Modifier.align(Alignment.BottomEnd))   {

                    Image(
                        painter = painterResource(id = R.drawable.headset),
                        contentDescription ="",
                        modifier = Modifier.size(130.dp) ,
                        alignment = Alignment.CenterEnd
                    )
                    }

                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            CustomText(text = stringResource(id = R.string.categories))
            Spacer(modifier = Modifier.height(4.dp))
            if (viewModel.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = kPrimaryColor)
                }
            }else{ categories?.data?.data?.let { categoriesData ->
                CategorySection(categoriesData)
            }}
            Spacer(modifier = Modifier.height(8.dp))

        }

        item{
            CustomText(text = stringResource(id = R.string.recommendations))



            Spacer(modifier = Modifier.height(4.dp))

            if (viewModel.products.collectAsState().value==null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = kPrimaryColor)
                }
            }else{ homeData?.dataa?.products?.let { products ->
                ProductGrid(products,viewModel)
            }}
        }
    }
}


@Composable
private fun CustomText(text:String) {
    Row (modifier =
    Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.interregular)),
                fontWeight = FontWeight.Bold
            ),
        )
        Text(
            stringResource(id = R.string.see_all),
            style = TextStyle(
                color = kPrimaryColor,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.interregular)),
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            ),
        )

    }

}

@Composable
private fun CustomIcon(icon: Painter,onClick:()->Unit) {
    Box(modifier = Modifier
        .size(35.dp)
        .clip(CircleShape)
        .background(lightGreyColor.copy(alpha = 0.4f))) {
        IconButton(onClick = onClick) {
            Icon(
                painter = icon,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp),

            )
        }


    }
}


@Composable
fun AutoScrollingBannerSection(banners: List<Banners>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val bannerCount = banners.size

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(2000L)

                val nextIndex = (listState.firstVisibleItemIndex + 1) % bannerCount
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(36.dp))
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 3),
        ) {
            itemsIndexed(banners) { index, banner ->
                val scale = if (index == listState.firstVisibleItemIndex) 1.2f else 1f

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    AsyncImage(
                        model = banner.image,
                        contentDescription = "Banner $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        // PageIndicator(items = banners, currentPage = listState.firstVisibleItemIndex + 1, kPrimaryColor)
    }
}




@Composable
fun CategorySection(categories: List<DataCategories>) {
    val navigator = LocalNavigator.currentOrThrow
    val categoriesPhotos= mutableListOf(
        R.drawable.chip
        ,R.drawable.cor
        ,R.drawable.sports
        ,R.drawable.led
        ,R.drawable.clothes
    )


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
           items(categories.size) { index ->
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                        .clickable {
                            navigator.push(CategoryProductsScreen(categories[index].id ?: 0))
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = categoriesPhotos[index],
                        contentDescription = categories[index].name,
                        modifier = Modifier
                            .padding(5.dp)
                            .border(1.dp, lightGreyColor, RoundedCornerShape(10.dp))
                            .size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = (categories[index].name?.substring(0, 5) + ".."),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

}

@Composable
fun ProductGrid(products: List<Products>,viewModel:HomeViewModel) {
    LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 2),
        ) {
            items(products) { product ->
                ProductItem(product, viewModel )
            }
        }

}



@Composable
fun ProductItem(product: Products,viewModel: HomeViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .height(195.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { navigator.push(DetailsScreen(product)) },
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {
            AsyncImage(
                model = product.image,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(14.dp),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.interregular)),
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${product.price} "+ stringResource(id = R.string.currency_egp),
                        style = TextStyle(
                            color = kPrimaryColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.interregular)),
                            fontWeight = FontWeight.W400,
                        ),
                    )

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(kPrimaryColor.copy(alpha = 0.4f))
                    ) {
                        IconButton(
                            onClick = {  },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Add to Cart",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                if (product.oldPrice != null && product.oldPrice != product.price) {
                    Column {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "${product.oldPrice} "+stringResource(id = R.string.currency_egp),
                            style = TextStyle(
                                color = kPrimaryColor,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.interregular)),
                                fontWeight = FontWeight.W400,
                            ),
                            color = greyColor,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
        }

        if (product.discount != 0 && product.discount!=null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(kPrimaryColor.copy(alpha = 0.4f))
                    .padding(horizontal = 5.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "${product.discount}"+stringResource(id = R.string.percent_off),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        val isFavorite = viewModel.favoritesList.collectAsState().value.any { it.id == product.id }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
                .size(24.dp)
        ) {
            IconButton(
                onClick = {val productId: Int? = product.id
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
                            viewModel.addFavorite(
                                FavoriteRequest(productId = productId),
                                favoriteProduct
                            )}}  },
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = kPrimaryColor.copy(alpha = 0.4f)
                )
            }
        }
    }


}

@Composable
fun FavoriteIcon(product: Products, viewModel: HomeViewModel) {
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
                        viewModel.addFavorite(
                            FavoriteRequest(productId = productId),
                            favoriteProduct
                        )
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
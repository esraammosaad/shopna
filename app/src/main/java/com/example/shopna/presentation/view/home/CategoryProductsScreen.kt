package com.example.shopna.presentation.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.shopna.R
import com.example.shopna.data.model.Products
import com.example.shopna.presentation.view_model.FavoriteViewModel
import com.example.shopna.presentation.view_model.HomeViewModel
import com.example.shopna.ui.theme.kPrimaryColor

class CategoryProductsScreen(private val categoryId: Int) : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = viewModel()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(categoryId) {
            viewModel.getProductsByCategory(categoryId)
        }

        val categoryProducts = viewModel.categoryProducts.collectAsState()


        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 10.dp)){
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

            Spacer(modifier = Modifier.width(2.dp))
            Text(text =" Recommendations",style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.interregular)),
                fontWeight = FontWeight.Bold,
            ),)
        }
        //Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 30.dp)

        ) {

            val products = categoryProducts.value?.data?.let { dataCategories ->
                dataCategories.data.map { dataCategory ->
                    Products(
                        id = dataCategory.id ?: 0,
                        name = dataCategory.name ?: "",
                        image = dataCategory.image ?: "",
                        price = dataCategory.price ?: 0.0,
                        oldPrice = dataCategory.oldPrice,
                        discount = dataCategory.discount,
                        description = dataCategory.description ?: "",
                        images = dataCategory.images
                    )
                }
            }

            if (products != null && products.isNotEmpty()) {

//                ProductGrid(products = products, FavoriteViewModel())
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = kPrimaryColor
                    )
            }
        }
    }}
}

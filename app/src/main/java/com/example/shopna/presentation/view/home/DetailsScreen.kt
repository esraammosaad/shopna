package com.example.shopna.presentation.view.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.shopna.R
import com.example.shopna.data.model.Products
import com.example.shopna.presentation.view.authentication.CustomButton
import com.example.shopna.presentation.view.landing.PageIndicator
import com.example.shopna.presentation.view_model.FavoriteViewModel
import com.example.shopna.ui.theme.greyColor
import com.example.shopna.ui.theme.kPrimaryColor
import kotlinx.coroutines.delay
import java.util.Locale


class DetailsScreen(private val product: Products, private val favoriteViewModel: FavoriteViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pagerState = rememberPagerState(
            pageCount = { product.images.size },
            initialPage = 0,

            )
        var isFavorite by remember { mutableStateOf(product.inFavorites) }
        var isMoreClicked by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableIntStateOf(0) }
        var quantity by remember { mutableIntStateOf(1) }
        val favoriteIsLoading by favoriteViewModel.isLoading.collectAsState()




        LaunchedEffect(pagerState.currentPage) {
            while (true) {
                delay(3000)
                val nextPage = if(pagerState.currentPage+1 == pagerState.pageCount) 0 else pagerState.currentPage + 1
                pagerState.scrollToPage(nextPage)
            }
        }



        Box(
            modifier = Modifier
                .systemBarsPadding()


        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                item{
                  Box( modifier = Modifier
                      .wrapContentSize()
                      .clip(
                          RoundedCornerShape(
                              bottomStart = 32.dp,
                              bottomEnd = 32.dp
                          )
                      )
                      .background(Color.White)
                     ){
                      Column (
                          modifier = Modifier
                              .wrapContentSize()

                      ){
                          IconButton(
                              onClick = {
                                  navigator.pop()
                              },
                          ) {
                              Icon(
                                  imageVector = Icons.Default.KeyboardArrowLeft,
                                  contentDescription = "Back to Home",
                                  tint = Color.Black.copy(0.7f),
                                  modifier = Modifier.size(35.dp)


                              )
                          }
                          if (product.images.isNotEmpty()) {
                              HorizontalPager(
                                  state =pagerState ,
                                  modifier = Modifier
                                      .wrapContentSize()
                              ) {index->
                                  AsyncImage(
                                      model = product.images[index],
                                      contentDescription = product.name,
                                      contentScale = ContentScale.Fit,
                                      modifier = Modifier
                                          .fillMaxWidth()
                                          .height(LocalConfiguration.current.screenHeightDp.dp / 3)
                                  )


                              }
                              Spacer(modifier = Modifier.height(16.dp))



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

                          PageIndicator(items = product.images, currentPage = pagerState.currentPage,
                              kPrimaryColor)

                          Spacer(modifier = Modifier.height(16.dp))
                      }
                      if (product.discount != 0 && product.discount!=null) {
                          Box(
                              modifier = Modifier.align(alignment = Alignment.TopEnd)
                                  .padding(5.dp)
                                  .clip(RoundedCornerShape(4.dp))
                                  .background(kPrimaryColor.copy(alpha = 0.6f))
                                  .padding(horizontal = 8.dp, vertical = 10.dp)
                          ) {
                              Text(
                                  text = "${product.discount}"+stringResource(id = R.string.percent_off),
                                  color = Color.White,
                                  fontSize = 16.sp,
                                  fontWeight = FontWeight.Bold
                              )
                          }
                      }

                  }


                    Column (modifier = Modifier.padding(horizontal = 16.dp)){
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Column {
                                Text(
                                    text =String.format(Locale.getDefault(),"%.2f", product.price)+"EGP",
                                    style = TextStyle(
                                        fontSize = LocalConfiguration.current.fontScale.times(18).sp,
                                        fontFamily = FontFamily(Font(R.font.interregular)),
                                        fontWeight = FontWeight.W400,
                                        color = kPrimaryColor

                                    ),
                                )
                                if (product.oldPrice != null && product.oldPrice != product.price) {
                                    Column {
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "${product.oldPrice} "+ stringResource(id = R.string.currency_egp),
                                            style = TextStyle(
                                                color = kPrimaryColor,
                                                fontSize = LocalConfiguration.current.fontScale.times(18).sp,
                                                fontFamily = FontFamily(Font(R.font.interregular)),
                                                fontWeight = FontWeight.W400,
                                            ),
                                            color = greyColor,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    }
                                }

                            }


                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        favoriteViewModel.addOrDeleteFavorite(product.id!!)
                                        isFavorite=!isFavorite!!
                                        favoriteViewModel.fetchFavorites()
                                        product.inFavorites=isFavorite
                                        selectedIndex=product.id!!


                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    if(favoriteIsLoading && selectedIndex==product.id)
                                        CircularProgressIndicator(
                                            color = kPrimaryColor,
                                            modifier = Modifier.size(32.dp)) else Icon(
                                        imageVector = if (isFavorite==true) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = kPrimaryColor.copy(alpha = 0.6f)
                                    )
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))


                        Row( horizontalArrangement = Arrangement.SpaceBetween) {

                            Text(
                                text = product.name?:"",
                                style = TextStyle(
                                    fontSize = LocalConfiguration.current.fontScale.times(22).sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.W400,

                                    ),
                            )

                        }

                        HorizontalDivider(
                            color = Color.Gray.copy(alpha = 0.3f),
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = product.description ?: "No description available",
                                maxLines = if (isMoreClicked) Int.MAX_VALUE else 5,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.interregular)),
                                    color = Color(0xff868889)
                                ),
                                modifier = Modifier.weight(1f)
                            )


                            Text(
                                modifier = Modifier
                                    .clickable { isMoreClicked = !isMoreClicked },
                                text = if (isMoreClicked) " show less" else " show more",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.interregular)),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold

                                ),
                                textAlign = TextAlign.End
                            )
                        }



                        Spacer(modifier = Modifier.height(160.dp))


                    }
                }



            }

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(alignment = Alignment.BottomEnd)
            ){
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation =  CardDefaults. cardElevation(5.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text(text = "Quantity",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = Color(0xff868889)
                            )

                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .width(13.dp)
                                    .height(2.dp)
                                    .background(kPrimaryColor)
                                    .clickable {
                                        if(quantity>1)
                                            quantity--
                                    }
                            )
                            Spacer(modifier = Modifier.width(18.dp))
                            VerticalDivider(
                                Modifier
                                    .height(90.dp)
                                    .width(1.dp),
                                color = Color(0xffEBEBEB))
                            Spacer(modifier = Modifier.width(18.dp))

                            Text(text = quantity.toString() ,fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(18.dp))

                            VerticalDivider(
                                Modifier
                                    .height(90.dp)
                                    .width(1.dp),
                                color = Color(0xffEBEBEB)
                            )
                            Spacer(modifier = Modifier.width(18.dp))
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = kPrimaryColor,
                                modifier = Modifier
                                    .clickable {
                                        quantity++
                                    }
                                    .size(18.dp)

                            )


                        }

                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Box{

                    CustomButton(
                        text = "Add to cart",
                        onClick = { /*TODO*/ }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.trolley),
                        tint = Color.White ,
                        contentDescription = "Add",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .padding(end = 18.dp)
                    )

                }

            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
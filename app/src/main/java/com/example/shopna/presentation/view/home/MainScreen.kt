package com.example.shopna.presentation.view.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.shopna.presentation.view_model.MainViewModel


class MainScreen(val mainViewModel: MainViewModel):Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavigationBar(mainViewModel) }
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                //SearchBar()

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    //HomeScreen(mainViewModel)
                    NavigatorContent(mainViewModel)

                }

            }
        }


    }
}

@Composable
fun NavigatorContent(mainViewModel: MainViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    navigator.push(MainScreen(mainViewModel))

    when (navigator.lastItem) {
        is FavoriteScreen -> FavoriteScreen(mainViewModel)
        is MainScreen -> HomeScreen(mainViewModel)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "", color = Color.Black) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = {
            IconButton(onClick = { /* TODO: Implement menu click */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Gray)
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: Implement notification click */ }) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Gray
                )
            }
        }
    )
}
/*
@Composable
fun SearchBar() {
    var text = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F0F0), shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = text.value,
                onValueChange = {  newvalue->
                           text.value=newvalue     },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
            )
        }
    }
}

*/


@Composable
fun BottomNavigationBar(mainViewModel: MainViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        NavigationBarItem(
            selected = navigator.lastItem is MainScreen,
            onClick = { navigator.push(MainScreen(mainViewModel)) },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint = Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = navigator.lastItem is FavoriteScreen,
            onClick = { navigator.push(FavoriteScreen(mainViewModel)) },
            icon = {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (navigator.lastItem is FavoriteScreen) Color.Magenta else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Implement cart click */ },
            icon = {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Implement profile click */ },
            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = Color.Gray
                )
            }
        )
    }
}
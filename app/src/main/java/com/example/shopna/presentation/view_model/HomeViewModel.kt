package com.example.shopna.presentation.view_model

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopna.data.model.Categories
import com.example.shopna.data.model.Favorite
import com.example.shopna.data.model.FavoriteProducts
import com.example.shopna.data.model.FavoriteRequest
import com.example.shopna.data.model.Home
import com.example.shopna.data.model.Products
import com.example.shopna.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale


class HomeViewModel() : ViewModel() {
    private val _homeData = MutableStateFlow<Home?>(null)
    val products: StateFlow<Home?> get() = _homeData


    private val _categories = MutableStateFlow<Categories?>(null)
    val categories: StateFlow<Categories?> get() = _categories

    private val _categoryProducts = MutableStateFlow<Categories?>(null)
    val categoryProducts: StateFlow<Categories?> get() = _categoryProducts

    private val _favorite = MutableStateFlow<Favorite?>(null)
    val favorite: StateFlow<Favorite?> get() = _favorite


    private val _addFavorite = MutableStateFlow<Favorite?>(null)
    val addFavorite : StateFlow<Favorite?> get() = _addFavorite



    private val _favoritesList = MutableStateFlow<List<FavoriteProducts>>(emptyList())
    val favoritesList: StateFlow<List<FavoriteProducts>> get() = _favoritesList

    private val api = RetrofitInstance.apiClient
    var isLoading by mutableStateOf(false)

    init {

        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)

    }



    fun getHomeData() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.getHomeData()
                if (response.isSuccessful) {
                    _homeData.value = response.body()
                    println("products data: ===============================================================$products")


                } else {

                    println("Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
            }finally {
                isLoading = false

            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.getCategories()
                if (response.isSuccessful) {
                    _categories.value = response.body()
                    println("Categories data: ${response.body()}")
                    println("Categories data:=================================================== $categories")

                } else {
                    println("Error: ${response.errorBody()?.string()}")

                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
            }finally {
                isLoading = false

            }
        }
    }


    fun getProductsByCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = api.getProductsByCategory(categoryId)
                if (response.isSuccessful && response.body() != null) {
                    _categoryProducts.value = response.body()
                    println("Products: ${response.body()}")
                } else {
                    println("Error:${response.errorBody()?.string()}")
                    _categoryProducts.value = null
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                _categoryProducts.value = null
            }
        }
    }

    fun addFavorite(addFavoriteRequest: FavoriteRequest, product: FavoriteProducts) {
        viewModelScope.launch {
            try {
                val response = api.addToFavorites(addFavoriteRequest)
                if (response.isSuccessful) {
                    Log.d("Favorites", "Product added to favorites")
                    _favoritesList.value += product
                    //fetchFavorites()
                } else {
                    Log.e("Favorites", "Failed to add product to favorites")
                }
            } catch (e: Exception) {
                Log.e("Favorites", "Error: ${e.message}")
            }
        }
    }



    private fun fetchFavorites(): StateFlow<List<FavoriteProducts>> {
        return favoritesList
    }


      /*  fun fetchFavorites() {
            viewModelScope.launch {
                try {
                    val response = api.getFavorite()
                    if (response.isSuccessful) {

                        val favoriteDataList = response.body()?.data?.data


                        if (favoriteDataList != null) {
                              //FavoriteProducts
                            val favoritesList = favoriteDataList.mapNotNull { favoriteDataItem ->
                                favoriteDataItem.product
                            }

                              //MutableStateFlow
                            _favoritesList.value = favoritesList
                        } else {
                            Log.e("Favorites", "No favorite data found")
                        }
                    } else {
                        Log.e("Favorites", "Failed to fetch favorites: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("Favorites", "Error: ${e.message}")
                }
            }
        }
*/


    fun removeFavorite(productId: Int) {
        viewModelScope.launch {
            try {

                val response = api.removeFromFavorites(productId)
                if (response.isSuccessful) {
                    _favoritesList.value = _favoritesList.value.filter { it.id != productId }
                    Log.d("Favorites", "Product removed from favorites")
                } else {
                    Log.e("Favorites", "Failed to remove product from favorites")
                }
            } catch (e: Exception) {
                Log.e("Favorites", "Error: ${e.message}")
            }
        }
    }

}




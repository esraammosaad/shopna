package com.example.shopna.presentation.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.shopna.data.model.AddOrDeleteFavoriteResponse
import com.example.shopna.data.model.GetFavoriteResponse
import com.example.shopna.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class FavoriteViewModel(private val navigator: Navigator, val context: Context) : ViewModel() {
    private val _favorite = MutableStateFlow<AddOrDeleteFavoriteResponse?>(null)
    val favorite: StateFlow<AddOrDeleteFavoriteResponse?> get() = _favorite

    private val _favoriteData = MutableStateFlow<GetFavoriteResponse?>(null)
    val favoriteData:StateFlow<GetFavoriteResponse?> get() = _favoriteData
    val authViewModel:AuthViewModel=AuthViewModel( navigator, context)
    private val api = RetrofitInstance.apiClient
    var isLoading by mutableStateOf(false)

    init {

        fetchFavorites()
        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)

    }



    fun addOrDeleteFavorite( productID:Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.addOrDeleteFavorites(productID)
                if (response.isSuccessful) {
                    Log.d("Favorites", "Product added to favorites")
                    _favorite.value =response.body()
                } else {
                    Log.e("Favorites", "Failed to add product to favorites")
                }
            } catch (e: Exception) {
                Log.e("Favorites", "Error: ${e.message}")
            }finally {
                isLoading = false
            }
        }
    }



    fun fetchFavorites() {
          viewModelScope.launch {
              isLoading = true
              try {
                  authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                  val response = api.getFavorite()
                  if (response.isSuccessful) {
                      _favoriteData.value=response.body()
                  } else {
                      Log.e("Favorites", "Failed to fetch favorites: ${response.errorBody()?.string()}")
                  }
              } catch (e: Exception) {
                  Log.e("Favorites", "Error: ${e.message}")
              }finally {
                  isLoading = false

              }
          }
      }



}




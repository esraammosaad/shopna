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



class FavoriteViewModel(navigator: Navigator, context: Context) : ViewModel() {

    private val _favorite = MutableStateFlow<AddOrDeleteFavoriteResponse?>(null)
    val favorite: StateFlow<AddOrDeleteFavoriteResponse?> get() = _favorite

    private val _favoriteData = MutableStateFlow<GetFavoriteResponse?>(null)
    val favoriteData: StateFlow<GetFavoriteResponse?> get() = _favoriteData

    val authViewModel: AuthViewModel = AuthViewModel(navigator, context)
    private val api = RetrofitInstance.apiClient
    var isLoading by mutableStateOf(false)

    private var dataFetched = false

    init {
        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)
    }

    fun fetchFavorites(forceRefresh: Boolean = false) {
        if (dataFetched && !forceRefresh) return

        viewModelScope.launch {
            isLoading = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.getFavorite()
                if (response.isSuccessful) {
                    _favoriteData.value = response.body()
                    dataFetched = true // Mark data as fetched
                } else {
                    Log.e("Favorites", "Failed to fetch favorites: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Favorites", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun addOrDeleteFavorite(productId: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.addOrDeleteFavorites(productId)
                if (response.isSuccessful) {
                    Log.d("Favorites", "Product added/removed from favorites")
                    _favorite.value = response.body()
                    fetchFavorites(forceRefresh = true) // Refresh favorites after update
                } else {
                    Log.e("Favorites", "Failed to add/remove product to/from favorites")
                }
            } catch (e: Exception) {
                Log.e("Favorites", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}



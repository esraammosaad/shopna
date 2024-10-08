package com.example.shopna.presentation.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.shopna.data.model.AddOrRemoveCartResponse
import com.example.shopna.data.model.GetCartResponse
import com.example.shopna.data.model.UpdateCartResponse
import com.example.shopna.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale



class CartViewModel(navigator: Navigator, context: Context) : ViewModel() {

    private val _cart = MutableStateFlow<AddOrRemoveCartResponse?>(null)
    val cart: StateFlow<AddOrRemoveCartResponse?> get() = _cart

    private val _cartData = MutableStateFlow<GetCartResponse?>(null)
    val cartData: StateFlow<GetCartResponse?> get() = _cartData
    private val _updateCart = MutableStateFlow<UpdateCartResponse?>(null)
    val updateCart: StateFlow<UpdateCartResponse?> get() = _updateCart

    val authViewModel: AuthViewModel = AuthViewModel(navigator, context)
    private val api = RetrofitInstance.apiClient
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isUpdateCartLoading = MutableStateFlow(false)
    val isUpdateCartLoading: StateFlow<Boolean> get() = _isUpdateCartLoading

    private var dataFetched = false

    init {
        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)
    }

    fun fetchCartData(forceRefresh: Boolean = false) {
        if (dataFetched && !forceRefresh) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.getCart()
                if (response.isSuccessful) {
                    _cartData.value = response.body()
                    dataFetched = true
                    _isLoading.value = false

                } else {
                    Log.e("Cart", "Failed to fetch cart: ${response.errorBody()?.string()}")
                    _isLoading.value = false

                }
            } catch (e: Exception) {
                Log.e("Cart", "Error: ${e.message}")
                _isLoading.value = false

            } finally {

            }
        }
    }

    fun addOrDeleteCart(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.addOrDeleteCart(productId)
                if (response.isSuccessful) {
                    Log.d("Cart", "Product added/removed from cart")
                    _cart.value = response.body()
                    fetchCartData(forceRefresh = true).let {
                        _isLoading.value = false

                    } // Refresh cart after update
                } else {
                    Log.e("Cart", "Failed to add/remove product to/from cart")
                }
            } catch (e: Exception) {
                Log.e("Cart", "Error: ${e.message}")
            } finally {
            }
        }
    }
    fun updateCart(productId: Int , quantity:Int) {
        viewModelScope.launch {
            _isUpdateCartLoading.value = true
            try {
                authViewModel.getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
                val response = api.updateCart(productId,quantity)
                if (response.isSuccessful) {
                    Log.d("Cart", "Product updated successfully")
                    _updateCart.value = response.body()
                    fetchCartData(forceRefresh = true).let {
                        _isUpdateCartLoading.value = false

                    } // Refresh cart after update


                } else {
                    Log.e("Cart", "Failed to update product")
                    _isUpdateCartLoading.value = false

                }
            } catch (e: Exception) {
                Log.e("Cart", "Error: ${e.message}")
                _isUpdateCartLoading.value = false

            } finally {
            }
        }
    }
}



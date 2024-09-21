package com.example.shopna.presentation.view_model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.shopna.data.model.GetUserResponse
import com.example.shopna.data.model.LoginRequest
import com.example.shopna.data.model.LoginResponse
import com.example.shopna.data.model.RegisterRequest
import com.example.shopna.data.model.RegisterResponse
import com.example.shopna.data.network.RetrofitInstance
import com.example.shopna.presentation.view.home.HomeScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class AuthViewModel(private val navigator: Navigator, val context: Context) : ViewModel() {

    private val _userRegisterResponse = MutableStateFlow<RegisterResponse?>(null)
    val userRegisterResponse: StateFlow<RegisterResponse?> get() = _userRegisterResponse

    private val _userLoginResponse = MutableStateFlow<LoginResponse?>(null)
    val userLoginResponse: StateFlow<LoginResponse?> get() = _userLoginResponse

    private val _user = MutableStateFlow<GetUserResponse?>(null)
    val user: StateFlow<GetUserResponse?> get() = _user

    private val api = RetrofitInstance.apiClient

    init {
        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)
    }

    var isLoading by mutableStateOf(false)


    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.register(registerRequest)
                if (response.isSuccessful&&response.body()?.status==true) {
                    _userRegisterResponse.value = response.body()
                    _userRegisterResponse.value?.let {
                        RetrofitInstance.setAuthToken(it.data.token)
                    }
                    if (getUser()) {
                        println("User registered successfully: ${_user.value}")
                        navigator.push(HomeScreen(MainViewModel()))
                    }
                } else {
                    Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.login(loginRequest)
                if (response.isSuccessful&&response.body()?.status==true) {
                    _userLoginResponse.value = response.body()
                    _userLoginResponse.value?.let { RetrofitInstance.setAuthToken(it.data.token) }
                    if (getUser()) {
                        println("==========================================================${_user.value}")
                      navigator.push(HomeScreen(MainViewModel()))
                    }
                } else {
                    Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun getUser(): Boolean {
        return try {
            val response = api.getUser()
            if (response.isSuccessful) {
                _user.value = response.body()
                true
            } else {
                Toast.makeText(context, "Failed to retrieve user", Toast.LENGTH_SHORT).show()
                false
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
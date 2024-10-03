package com.example.shopna.presentation.view_model

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.shopna.data.model.*
import com.example.shopna.data.network.RetrofitInstance
import com.example.shopna.presentation.view.home.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class AuthViewModel(private val navigator: Navigator, private val context: Context) : ViewModel() {

    private val _userRegisterResponse = MutableStateFlow<RegisterResponse?>(null)
    val userRegisterResponse: StateFlow<RegisterResponse?> get() = _userRegisterResponse

    private val _userLoginResponse = MutableStateFlow<LoginResponse?>(null)
    val userLoginResponse: StateFlow<LoginResponse?> get() = _userLoginResponse

    private val _user = MutableStateFlow<GetUserResponse?>(null)
    val user: StateFlow<GetUserResponse?> get() = _user

    private val api = RetrofitInstance.apiClient
    val homeViewModel = HomeViewModel()


    init {
        if(getAuthToken()!=null){
            getAuthToken()?.let { RetrofitInstance.setAuthToken(it) }
            getUser()
        homeViewModel.getHomeData()
        homeViewModel.getCategories()
        }
        val languageCode = Locale.getDefault().language
        RetrofitInstance.setLanguage(languageCode)
    }



     fun getAuthToken(): String? {
        val sharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.register(registerRequest)
                if (response.isSuccessful && response.body()?.status == true) {
                    _userRegisterResponse.value = response.body()
                    _userRegisterResponse.value?.let {
                        RetrofitInstance.setAuthToken(it.data.token)
                    }
                    saveAuthToken(context, _userLoginResponse.value?.data?.token.toString())
                    getUser().let {
                            homeViewModel.getHomeData().let {
                                homeViewModel.getCategories().let {
                                    navigator.push(MainScreen(homeViewModel,user))
                                    _isLoading.value = false

                                }
                            }

                    }

                } else {
                    Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.login(loginRequest)
                if (response.isSuccessful && response.body()?.status == true) {
                    _userLoginResponse.value = response.body()
                    _userLoginResponse.value?.let {
                        RetrofitInstance.setAuthToken(it.data.token)
                    }
                    saveAuthToken(context, _userLoginResponse.value?.data?.token.toString())
                    getUser().let {
                            homeViewModel.getHomeData().let {
                                homeViewModel.getCategories().let {
                                    navigator.push(MainScreen(homeViewModel,user))
                                    _isLoading.value = false

                                }
                            }

                    }

                } else {
                    Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            } finally {
            }
        }
    }

      private fun getUser() {
          viewModelScope.launch {
              val response = api.getUser()
              try {

                  Log.d("AuthViewModel", "Get User Response: ${response.body()}")
                  if (response.body()?.status==true) {
                      _user.value = response.body()
                  } else {
                      Log.e("AuthViewModel", "Error: ${response.code()} - ${response.message()}")
                      Toast.makeText(context, "Failed to retrieve user", Toast.LENGTH_SHORT).show()
                  }
              } catch (e: Exception) {
                  Log.e("AuthViewModel", "Error: ${e.message}", e)
                  Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
              }
          }


    }

//    private suspend fun getHomeData(): Boolean {
//        return try {
//            _isLoading.value = true
//            homeViewModel.getHomeData() // Ensure this is a suspend function
//            homeViewModel.getCategories()
//
//            if (homeViewModel.products.value != null && homeViewModel.categories.value != null) {
//                Log.d("AuthViewModel", "Home data loaded successfully")
//                true
//
//            } else {
//                Log.e("AuthViewModel", "Home data not loaded")
//                false
//            }
//        } catch (e: Exception) {
//            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//            false
//        } finally {
//        }
//    }

    private fun saveAuthToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
}

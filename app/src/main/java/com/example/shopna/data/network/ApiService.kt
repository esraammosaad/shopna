package com.example.shopna.data.network

import com.example.shopna.data.model.GetUserResponse
import com.example.shopna.data.model.LoginRequest
import com.example.shopna.data.model.LoginResponse
import com.example.shopna.data.model.RegisterRequest
import com.example.shopna.data.model.RegisterResponse
import com.example.shopna.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService{

    @POST("register")
    suspend fun register( @Body registerRequest:RegisterRequest) : Response<RegisterResponse>
    @POST("login")
    suspend fun login( @Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET("profile")
    suspend fun getUser() :Response<GetUserResponse>

}

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val authInterceptor= AuthInterceptor()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl("https://student.valuxapps.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiClient = retrofit.create(ApiService::class.java)
    fun setAuthToken(token:String){
        authInterceptor.setToken(token)
    }
    fun setLanguage(language:String){
        authInterceptor.setLanguage(language)
    }
}
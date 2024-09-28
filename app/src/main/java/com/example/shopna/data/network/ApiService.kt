package com.example.shopna.data.network

import com.example.shopna.data.model.Categories
import com.example.shopna.data.model.Favorite
import com.example.shopna.data.model.FavoriteRequest
import com.example.shopna.data.model.GetUserResponse
import com.example.shopna.data.model.Home
import com.example.shopna.data.model.LoginRequest
import com.example.shopna.data.model.LoginResponse
import com.example.shopna.data.model.Products
import com.example.shopna.data.model.RegisterRequest
import com.example.shopna.data.model.RegisterResponse
import com.example.shopna.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ApiService{

    @POST("register")
    suspend fun register( @Body registerRequest:RegisterRequest) : Response<RegisterResponse>
    @POST("login")
    suspend fun login( @Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET("profile")
    suspend fun getUser() :Response<GetUserResponse>

    @GET("home")
    suspend fun getHomeData():Response<Home>


    @GET("categories")
    suspend fun getCategories():Response<Categories>

    @GET("categories/{id}")
    suspend fun getProductsByCategory(@Path("id") categoryId: Int): Response<Categories>

    @GET("favorites")
    suspend fun getFavorite():Response<Favorite>

    @POST("favorites")
    suspend fun addToFavorites(@Body favoriteRequest: FavoriteRequest): Response<Favorite>


    @DELETE("favorites/{id}")
    suspend fun removeFromFavorites(@Path("id") productId: Int): Response<Unit>




}

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val authInterceptor= AuthInterceptor()
    private val okHttpClient = OkHttpClient.Builder() .connectTimeout(30, TimeUnit.SECONDS) // Increase connection timeout
        .readTimeout(30, TimeUnit.SECONDS) // Increase read timeout
        .writeTimeout(30, TimeUnit.SECONDS) // Increase write timeout
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
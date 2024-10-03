package com.example.shopna.data.network

import com.example.shopna.data.model.AddOrDeleteFavoriteResponse
import com.example.shopna.data.model.CategoryDetailsResponse
import com.example.shopna.data.model.GetCategoryResponse
import com.example.shopna.data.model.GetFavoriteResponse
import com.example.shopna.data.model.GetUserResponse
import com.example.shopna.data.model.Home
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
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    suspend fun getCategories():Response<GetCategoryResponse>

    @GET("categories/{id}")
    suspend fun getProductsByCategory(@Path("id") categoryId: Int): Response<CategoryDetailsResponse>

    @GET("favorites")
    suspend fun getFavorite():Response<GetFavoriteResponse>

    @FormUrlEncoded
    @POST("favorites")
    suspend fun addOrDeleteFavorites(@Field("product_id") productId: Int): Response<AddOrDeleteFavoriteResponse>



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


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://student.valuxapps.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiClient: ApiService = retrofit.create(ApiService::class.java)
    fun setAuthToken(token:String){
        authInterceptor.setToken(token)
    }
    fun setLanguage(language:String){
        authInterceptor.setLanguage(language)
    }
}
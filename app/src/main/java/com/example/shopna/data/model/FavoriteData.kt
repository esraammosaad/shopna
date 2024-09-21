package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteData (

    @SerializedName("id"      ) var id      : Int?     = null,
    @SerializedName("product" ) var product : FavoriteProducts? = FavoriteProducts()

)
package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class Favorite (

    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    // @SerializedName("data"    ) var dataa    : Dataa?    = Dataa()
    @SerializedName("data") var data: FavoriteDataa? = FavoriteDataa()

)
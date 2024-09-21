package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class DataCategories (

    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("image" ) var image : String? = null

)
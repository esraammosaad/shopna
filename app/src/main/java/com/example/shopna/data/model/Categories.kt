package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class Categories (

    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    // @SerializedName("data"    ) var data    : Data?    = Data()
    @SerializedName("data") var data: DataCategoriess? = DataCategoriess()

)
package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class DataCategories (

    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("image" ) var image : String? = null,
    @SerializedName("price" ) var price : Double? = null,
    @SerializedName("old_price"    ) var oldPrice    : Double?              = null,
    @SerializedName("discount"     ) var discount    : Int?              = null,
   @SerializedName("description"  ) var description : String?           = null,
    @SerializedName("images"       ) var images      : ArrayList<String> = arrayListOf()
)
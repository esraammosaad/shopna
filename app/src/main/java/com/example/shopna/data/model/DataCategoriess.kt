package com.example.shopna.data.model

import com.google.gson.annotations.SerializedName

data class DataCategoriess (

    @SerializedName("current_page"   ) var currentPage  : Int?            = null,
    //  @SerializedName("data"           ) var data         : ArrayList<Data> = arrayListOf(),
    @SerializedName("data") var data: List<DataCategories> = listOf(),
    @SerializedName("first_page_url" ) var firstPageUrl : String?         = null,
    @SerializedName("from"           ) var from         : Int?            = null,
    @SerializedName("last_page"      ) var lastPage     : Int?            = null,
    @SerializedName("last_page_url"  ) var lastPageUrl  : String?         = null,
    @SerializedName("next_page_url"  ) var nextPageUrl  : String?         = null,
    @SerializedName("path"           ) var path         : String?         = null,
    @SerializedName("per_page"       ) var perPage      : Int?            = null,
    @SerializedName("prev_page_url"  ) var prevPageUrl  : String?         = null,
    @SerializedName("to"             ) var to           : Int?            = null,
    @SerializedName("total"          ) var total        : Int?            = null

)
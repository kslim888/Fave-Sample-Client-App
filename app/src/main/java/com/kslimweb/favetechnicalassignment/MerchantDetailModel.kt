package com.kslimweb.favetechnicalassignment

import com.google.gson.annotations.SerializedName

data class MerchantDetail(
    @SerializedName("company_name")
    val companyName: String,

    @SerializedName("_id")
    val id: String,

    @SerializedName("location")
    val location: List<String>
)
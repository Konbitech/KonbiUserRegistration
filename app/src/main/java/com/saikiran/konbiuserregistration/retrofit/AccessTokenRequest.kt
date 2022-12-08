package com.saikiran.konbiuserregistration.retrofit

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(
    @SerializedName("grant_type")
    var grant_type: String,
    @SerializedName("client_id")
    var client_id: String,
    @SerializedName("client_secret")
    var client_secret: String
)

package com.saikiran.konbiuserregistration.retrofit

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    @SerializedName("access_token")
    var access_token: String,
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("password")
    var password: String? = "",
    @SerializedName("user_role")
    var user_role: String? = "",
    @SerializedName("ccw_id1")
    var ccw_id1: String? = ""
)

package com.sanket.newsapp.api.models.response

import com.google.gson.annotations.SerializedName

abstract class BaseResponse (
        @SerializedName("status")
        val status: String,

        @SerializedName("code")
        val code: String?,

        @SerializedName("message")
        val message: String?

)
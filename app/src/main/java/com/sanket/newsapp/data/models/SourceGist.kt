package com.sanket.newsapp.data.models

import com.google.gson.annotations.SerializedName

class SourceGist(

    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String

)
package com.sanket.newsapp.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class SourceGist (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String

    ): Parcelable
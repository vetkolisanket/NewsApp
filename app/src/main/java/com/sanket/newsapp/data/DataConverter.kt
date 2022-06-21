package com.sanket.newsapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanket.newsapp.data.models.SourceGist

class DataConverter {

    @TypeConverter
    fun fromSourceToString(value: SourceGist): String {
        val gson = Gson()
        val type = object : TypeToken<SourceGist>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSource(value: String): SourceGist {
        val gson = Gson()
        val type = object : TypeToken<SourceGist>() {}.type
        return gson.fromJson(value, type)
    }
}
package com.venza.wheaterapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun setIntModel(ints: List<ForecastRow>): String = Gson().toJson(ints)

    @TypeConverter
    fun getIntModel(jsonModel: String): List<ForecastRow> {
        val listType: Type = object : TypeToken<List<ForecastRow>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }


}
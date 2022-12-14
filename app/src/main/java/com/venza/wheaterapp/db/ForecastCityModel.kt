package com.venza.wheaterapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "forecast_city_table")
@TypeConverters(Converters::class)
data class ForecastCityModel(

    @field:PrimaryKey(autoGenerate = false)
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "temp")
    val temp: Double? = null,

    @ColumnInfo(name = "icon")
    val icon: String? = null,

    @ColumnInfo(name = "time")
    val time: String? = null,

    @ColumnInfo(name = "forecastRows")
    val forecastRows: List<ForecastRow>? = null,

    )

data class ForecastRow(

    @ColumnInfo(name = "main")
    val main: String? = null,

    @ColumnInfo(name = "icon")
    val icon: String? = null,

    @ColumnInfo(name = "time")
    val time: String? = null,

    @ColumnInfo(name = "temp")
    val temp: Double? = null,

    )
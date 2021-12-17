package com.adrc95.punkappsample.data.db

import androidx.room.TypeConverter
import com.adrc95.punkappsample.data.Hop
import com.adrc95.punkappsample.data.Malt
import com.adrc95.punkappsample.data.MashTemp
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RequestConverters {

    @TypeConverter
    fun listStringToJsonStr(listMyModel: List<String>?): String = Json.encodeToString(listMyModel)

    @TypeConverter
    fun jsonStrToListString(jsonStr: String?): List<String>? =
    jsonStr?.let { Json.decodeFromString<List<String>>(jsonStr) }

    @TypeConverter
    fun listMashToJsonStr(listMash: List<MashTemp>?): String = Json.encodeToString(listMash)

    @TypeConverter
    fun jsonMashToListString(jsonStr: String?): List<MashTemp>? =
        jsonStr?.let { Json.decodeFromString<List<MashTemp>>(jsonStr) }

    @TypeConverter
    fun listMaltToJsonStr(listMalt: List<Malt>?): String = Json.encodeToString(listMalt)

    @TypeConverter
    fun jsonMaltToListString(jsonStr: String?): List<Malt>? =
        jsonStr?.let { Json.decodeFromString<List<Malt>>(jsonStr) }

    @TypeConverter
    fun listHopToJsonStr(listHop: List<Hop>?): String = Json.encodeToString(listHop)

    @TypeConverter
    fun jsonHopToListString(jsonStr: String?): List<Hop>? =
        jsonStr?.let {  Json.decodeFromString<List<Hop>>(jsonStr) }

}
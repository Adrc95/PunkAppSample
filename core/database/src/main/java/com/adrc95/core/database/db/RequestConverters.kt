package com.adrc95.core.database.db

import androidx.room.TypeConverter
import com.adrc95.core.database.entity.BoilVolumeEntity
import com.adrc95.core.database.entity.IngredientsEntity
import com.adrc95.core.database.entity.MethodEntity
import kotlinx.serialization.json.Json

class RequestConverters {

    @TypeConverter
    fun listStringToJsonStr(values: List<String>?): String = Json.encodeToString(values)

    @TypeConverter
    fun jsonStrToListString(jsonStr: String?): List<String>? = jsonStr?.let {
        Json.decodeFromString<List<String>>(it)
    }

    @TypeConverter
    fun ingredientsToJsonStr(ingredients: IngredientsEntity?): String = Json.encodeToString(ingredients)

    @TypeConverter
    fun jsonStrToIngredients(jsonStr: String?): IngredientsEntity? = jsonStr?.let {
        Json.decodeFromString<IngredientsEntity?>(it)
    }

    @TypeConverter
    fun boilVolumeToJsonStr(boilVolume: BoilVolumeEntity?): String = Json.encodeToString(boilVolume)

    @TypeConverter
    fun jsonStrToBoilVolume(jsonStr: String?): BoilVolumeEntity? = jsonStr?.let {
        Json.decodeFromString<BoilVolumeEntity?>(it)
    }

    @TypeConverter
    fun methodToJsonStr(method: MethodEntity?): String = Json.encodeToString(method)

    @TypeConverter
    fun jsonStrToMethod(jsonStr: String?): MethodEntity? = jsonStr?.let {
        Json.decodeFromString<MethodEntity?>(it)
    }

}

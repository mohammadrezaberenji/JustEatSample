package com.example.justeatsample.utils

import androidx.room.TypeConverter
import com.example.justeatsample.data.source.local_models.Restaurant
import com.google.gson.Gson

class ModelConverter {
    companion object {
        @TypeConverter
        fun convertFromModelToJsonString(restaurant: Restaurant): String {
            return Gson().toJson(restaurant)
        }

        @TypeConverter
        fun convertFromJsonToRestaurant(json: String): Restaurant {
            return Gson().fromJson(json, Restaurant::class.java)
        }
    }

}
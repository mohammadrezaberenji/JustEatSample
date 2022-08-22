package com.example.justeatsample.data.source.local_models

import android.util.Log

data class SortValueItem(
    val sortingValuesEnum: Restaurant.SortingValuesEnum,
    var isSelected: Boolean,
    var name: String
)
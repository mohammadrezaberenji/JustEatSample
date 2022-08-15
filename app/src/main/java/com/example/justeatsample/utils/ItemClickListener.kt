package com.example.justeatsample.utils

import com.example.justeatsample.data.source.local_models.Restaurant

interface ItemClickListener {

    fun onItemClick(item : Any , position : Int)
}
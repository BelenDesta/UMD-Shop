package com.example.terpshop

import java.io.Serializable

data class ItemData(
    val name: String,
    val category: String,
    val details: String,
    val relevance: String
) : Serializable

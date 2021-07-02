package com.example.baseproject.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class User(
    @Json(name = "items")
    val items: List<Item>? = null
)

@Parcelize
data class Item(
    @Json(name = "login")
    val name: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "score")
    val score: Double
) : Parcelable

package com.example.requestmaker.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
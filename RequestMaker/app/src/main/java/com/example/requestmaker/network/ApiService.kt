package com.example.requestmaker.network
import com.example.requestmaker.model.Message

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getMessages(): Call<List<Message>>
}

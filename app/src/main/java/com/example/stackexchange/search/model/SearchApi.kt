package com.example.stackexchange.search.model

import retrofit2.http.GET

interface SearchApi {

    @GET("2.2/questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow")
    suspend fun getQuestions(): Questions
}
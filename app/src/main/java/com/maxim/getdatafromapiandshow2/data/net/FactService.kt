package com.maxim.getdatafromapiandshow2.data.net

import retrofit2.Call
import retrofit2.http.GET

interface FactService {
    @GET("https://api.api-ninjas.com/v1/facts?X-Api-Key=zby0v4LYDh04KmIxgbPZqw==CLlxL9Aqcl6PGNyP")
    fun getFact(): Call<List<FactServiceModel>>
}
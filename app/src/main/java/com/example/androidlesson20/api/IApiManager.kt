package com.example.androidlesson20.api

import com.example.androidlesson20.models.get.Recipe
import com.example.androidlesson20.models.get.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface  IApiManager {

    @GET("recipes")
    suspend fun getAllRecipes(): Response<RecipeResponse>


    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Path("id") id: Int
    ): Response<Recipe>




}
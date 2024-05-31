package com.example.androidlesson20.api.repositories

import com.example.androidlesson20.api.IApiManager
import com.example.androidlesson20.models.get.Recipe
import com.example.androidlesson20.models.get.RecipeResponse
import retrofit2.Response
import javax.inject.Inject

class RecipeRepositories @Inject constructor(private val api: IApiManager) {

    suspend fun getAllRecipes(): Response<RecipeResponse> {
        return api.getAllRecipes()
    }

    suspend fun getRecipeById(id: Int): Response<Recipe> {
        return api.getRecipeById(id)
    }
}

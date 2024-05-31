package com.example.androidlesson20.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson20.api.repositories.RecipeRepositories
import com.example.androidlesson20.models.get.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: RecipeRepositories) : ViewModel() {

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun getRecipeById(id: Int) {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getRecipeById(id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val recipe = response.body()
                        if (recipe != null) {
                            _recipe.value = recipe!!
                        } else {
                            error.value = "Product not found"
                        }
                    } else {
                        error.value = "Failed to fetch product: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = e.localizedMessage ?: "An error occurred"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    loading.value = false
                }
            }
        }
    }


}
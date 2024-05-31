package com.example.androidlesson20.dependencyInjection

import com.example.androidlesson20.api.IApiManager
import com.example.androidlesson20.utilities.Constants.Base_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideIApiManager(retrofit: Retrofit): IApiManager {
        return  retrofit.create(IApiManager::class.java)
    }

}
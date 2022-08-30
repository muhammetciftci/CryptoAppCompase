package com.cryptoapp.dependencyinjection

import com.cryptoapp.repository.CryptoRepository
import com.cryptoapp.service.CryptoAPI
import com.cryptoapp.util.Constans.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(api: CryptoAPI) = CryptoRepository(api)

    @Singleton
    @Provides
    fun provideCryptoAPI() :CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }


}
package com.ozansan.hackernewsandroidclient.di

import com.ozansan.hackernewsandroidclient.network.api.HackerNewsApi
import com.ozansan.hackernewsandroidclient.network.repository.HackerNewsRepository
import com.ozansan.hackernewsandroidclient.network.repository.HackerNewsRepositoryImpl
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hacker-news.firebaseio.com/v0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHackerNewsApi(retrofit: Retrofit): HackerNewsApi {
        return retrofit.create(HackerNewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHackerNewsRepository(hackerNewsApi: HackerNewsApi): HackerNewsRepository {
        return HackerNewsRepositoryImpl(hackerNewsApi)
    }
}
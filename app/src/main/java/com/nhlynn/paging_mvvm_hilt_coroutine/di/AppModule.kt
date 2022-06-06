package com.nhlynn.paging_mvvm_hilt_coroutine.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhlynn.paging_mvvm_hilt_coroutine.network.ApiService
import com.nhlynn.paging_mvvm_hilt_coroutine.utils.MyConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun baseUrl() = MyConstants.BASE_URL

    @Provides
    fun gSon() = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun client(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val origin: Request = chain.request()
                val requestBuilder: Request.Builder = origin.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept", "text/plain")
                    .addHeader(
                        "Authorization",
                        "Client-ID TLri7glOpT7lEDbyuKe-a-3IlZEE-rS70Bo_lxxMe08"
                    )
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(baseUrl: String, gSon: Gson, client: OkHttpClient) =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()
            .create(ApiService::class.java)
}
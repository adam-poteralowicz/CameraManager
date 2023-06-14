package com.apap.cameraManager.di

import com.squareup.moshi.Moshi
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

private const val API_KEY = ""

@Module
@InstallIn(SingletonComponent::class)
object BackendModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor { chain ->
//                val original = chain.request()
//                val request = original.newBuilder()
//                    .header("Authorization", API_KEY)
//                    .method(original.method, original.body)
//                    .build()
//                chain.proceed(request)
//            }
            .readTimeout(Duration.ofSeconds(20))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://google.com")
            .build()
    }

    @Provides
    fun moshi(): Moshi = Moshi.Builder().build()
}
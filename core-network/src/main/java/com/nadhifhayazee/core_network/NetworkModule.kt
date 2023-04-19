package com.nadhifhayazee.core_network

import com.nadhif.hayazee.android_core.networking.OkhttpBuilder
import com.nadhif.hayazee.android_core.networking.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        return OkhttpBuilder.Builder().build()
    }


    @Provides
    fun providesNetworkService(
        okhttpClient: OkHttpClient
    ): NetworkService {
        return RetrofitBuilder.create(BuildConfig.BASE_USL, okhttpClient)
            .create(NetworkService::class.java)
    }
}
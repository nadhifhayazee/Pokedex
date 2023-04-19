package com.nadhifhayazee.core_data.module

import com.nadhifhayazee.core_data.repository.PokemonRepository
import com.nadhifhayazee.core_data.repository.PokemonRepositoryImpl
import com.nadhifhayazee.core_network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PokemonRepositoryModule {

    @Provides
    fun providesPokemonRepository(
        service: NetworkService
    ): PokemonRepository = PokemonRepositoryImpl(service)
}
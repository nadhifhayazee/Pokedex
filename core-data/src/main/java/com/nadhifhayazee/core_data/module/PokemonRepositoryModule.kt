package com.nadhifhayazee.core_data.module

import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import com.nadhifhayazee.core_data.repository.MyPokemonRepositoryImpl
import com.nadhifhayazee.core_data.repository.PokemonRepository
import com.nadhifhayazee.core_data.repository.PokemonRepositoryImpl
import com.nadhifhayazee.core_datastore.dao.PokemonDao
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

    @Provides
    fun providesMyPokemonRepository(
        pokemonDao: PokemonDao
    ): MyPokemonRepository = MyPokemonRepositoryImpl(pokemonDao)
}
package com.jayaram.goldsikka.di

import com.jayaram.goldsikka.model.repository.Repository
import com.jayaram.goldsikka.model.repository.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        repository: Repository
    ): RepositoryInterface
}
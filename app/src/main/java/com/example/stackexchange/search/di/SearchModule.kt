package com.example.stackexchange.search.di

import com.example.stackexchange.search.domain.SearchRepository
import com.example.stackexchange.search.domain.SearchRepositoryImpl
import com.example.stackexchange.search.domain.SearchTransformer
import com.example.stackexchange.search.domain.SearchTransformerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    @Binds
    abstract fun repo(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun transformer(impl: SearchTransformerImpl): SearchTransformer
}
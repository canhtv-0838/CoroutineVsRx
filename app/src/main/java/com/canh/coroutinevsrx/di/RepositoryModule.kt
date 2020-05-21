package com.canh.coroutinevsrx.di

import com.canh.coroutinevsrx.data.repository.MovieRepository
import com.canh.coroutinevsrx.data.repository.impl.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}

package com.canh.coroutinevsrx.di

import com.canh.coroutinevsrx.data.remote.ApiService
import com.canh.coroutinevsrx.data.remote.ApiService.createOkHttpCache
import com.canh.coroutinevsrx.data.remote.ApiServiceInterface
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single(named("cache")) { createOkHttpCache(get()) }
    single(named("header")) { ApiService.createHeaderInterceptor() } //Naming to identity element
    single(named("logging")) { ApiService.createLoggingInterceptor() }
    single {
        ApiService.createOkHttpClient(
            get(named("cache")),
            get(named("header")),
            get(named("logging"))
        )
    }
    single { ApiService.createRetrofit(get()) }
    single { createApiService<ApiServiceInterface>(get()) }
}

inline fun <reified T> createApiService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

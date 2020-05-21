package com.canh.coroutinevsrx.di

import com.canh.coroutinevsrx.data.local.prefs.AppPrefs
import com.canh.coroutinevsrx.data.local.prefs.PrefsHelper
import com.google.gson.Gson
import org.koin.dsl.module

val storageModule = module {
    single { Gson() }
    single<PrefsHelper> { AppPrefs(get(), get()) }
}

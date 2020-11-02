package com.forhad_naim.domain.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}
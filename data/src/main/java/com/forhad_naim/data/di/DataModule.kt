package com.forhad_naim.data.di

import android.content.Context
import com.forhad_naim.data.datasource.UserEngagementDataSource
import dagger.Module
import dagger.Provides
import java.io.IOException
import java.io.InputStream

class DataModule constructor(private val c: Context) {
    lateinit var context: Context

    init {
        this.context = c
    }

    fun provideContext(): Context {
        return context
    }
}
package com.forhad_naim.chaldal_assignment.di.module

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.forhad_naim.chaldal_assignment.view_model.AppViewModelFactory
import com.forhad_naim.data.datasource.UserEngagementDataSource
import com.forhad_naim.data.repository.UserEngagementRepositoryImpl
import com.forhad_naim.domain.di.DomainModule
import com.forhad_naim.domain.interfaceImpl.UserEngagementByStatusImpl
import com.forhad_naim.domain.interface_.UserEngagementByStatus
import com.forhad_naim.domain.repository.UserEngagementRepository
import com.forhad_naim.domain.usecase.UserEngagementUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(DomainModule::class))
class AppModule(
    private val c: Application
) {
    lateinit var context: Application

    init {
        this.context = c
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideAssetManager(context: Context): AssetManager {
        return context.assets
    }

    @Provides
    fun provideUserEngagementUseCase(
        userEngagementByStatus: UserEngagementByStatus,
        userEngagementRepository: UserEngagementRepository
    ): UserEngagementUseCase {
        return UserEngagementUseCase(userEngagementRepository, userEngagementByStatus)
    }

    @Provides
    fun provideUserEngagementDataSource(
        assetManager: AssetManager
    ): UserEngagementDataSource {
        return UserEngagementDataSource(assetManager)
    }

    @Provides
    fun provideUserEngagementByStatus(
        gson: Gson
    ): UserEngagementByStatus {
        return UserEngagementByStatusImpl(gson)
    }

    @Provides
    fun provideUserEngagementRepository(
        userEngagementDataSource: UserEngagementDataSource
    ): UserEngagementRepository {
        return UserEngagementRepositoryImpl(userEngagementDataSource)
    }

    @Provides
    fun getViewModelFactory(userEngagementUseCase: UserEngagementUseCase): AppViewModelFactory {
        return AppViewModelFactory(userEngagementUseCase)
    }
}
package com.forhad_naim.chaldal_assignment

import android.app.Application
import com.forhad_naim.chaldal_assignment.di.component.AppComponent
import com.forhad_naim.chaldal_assignment.di.component.DaggerAppComponent
import com.forhad_naim.chaldal_assignment.di.module.AppModule

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
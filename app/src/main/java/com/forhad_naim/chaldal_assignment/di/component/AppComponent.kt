package com.forhad_naim.chaldal_assignment.di.component

import com.forhad_naim.chaldal_assignment.di.module.AppModule
import com.forhad_naim.chaldal_assignment.ui.activities.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
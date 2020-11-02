package com.forhad_naim.chaldal_assignment.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forhad_naim.domain.usecase.UserEngagementUseCase

class AppViewModelFactory(private val userEngagementUseCase: UserEngagementUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserEngagementViewModel::class.java)) {
            return UserEngagementViewModel(userEngagementUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
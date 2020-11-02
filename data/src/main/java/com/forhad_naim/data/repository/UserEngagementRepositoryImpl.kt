package com.forhad_naim.data.repository

import com.forhad_naim.data.datasource.UserEngagementDataSource
import com.forhad_naim.domain.repository.UserEngagementRepository
import com.forhad_naim.domain.usecase.UserEngagementUseCase
import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class UserEngagementRepositoryImpl @Inject
constructor(private val userEngagementDataSource: UserEngagementDataSource) :
    UserEngagementRepository {
    override fun getUserEngagementData(): HashMap<String, JSONObject> {
        return userEngagementDataSource.getUserData()
    }
}
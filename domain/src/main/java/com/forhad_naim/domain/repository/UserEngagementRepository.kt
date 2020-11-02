package com.forhad_naim.domain.repository

import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject

interface UserEngagementRepository {
    fun getUserEngagementData(): HashMap<String, JSONObject>
}
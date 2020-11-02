package com.forhad_naim.domain.usecase

import com.forhad_naim.domain.interface_.UserEngagementByStatus
import com.forhad_naim.domain.repository.UserEngagementRepository
import com.forhad_naim.domain.utils.DomainConstants
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class UserEngagementUseCase @Inject constructor(
    private val repository: UserEngagementRepository,
    private val userEngagementByStatus: UserEngagementByStatus
) {
    fun getUserData(
        fromDate: String,
        toDate: String,
        status: DomainConstants.STATUS
    ): Observable<MutableList<String>> {
        when (status) {
            DomainConstants.STATUS.ACTIVE -> {
                return Observable.fromArray(
                    userEngagementByStatus.activeUser(
                        fromDate, toDate,
                        repository.getUserEngagementData()
                    )
                )
            }
            DomainConstants.STATUS.SUPER_ACTIVE -> {
                return Observable.fromArray(
                    userEngagementByStatus.superActiveUser(
                        fromDate, toDate,
                        repository.getUserEngagementData()
                    )
                )
            }
            DomainConstants.STATUS.BORED -> {
                return Observable.fromArray(
                    userEngagementByStatus.boredUser(
                        fromDate, toDate,
                        repository.getUserEngagementData()
                    )
                )
            }

        }

    }
}
package com.forhad_naim.chaldal_assignment.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forhad_naim.domain.usecase.UserEngagementUseCase
import com.forhad_naim.domain.utils.DomainConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserEngagementViewModel @Inject
constructor(
    private val userEngagementUseCase: UserEngagementUseCase
) :
    ViewModel() {

    private val mutableLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun requestUserListByEngageStatus(
        fromDate: String,
        toDate: String,
        status: DomainConstants.STATUS
    ) {
        compositeDisposable.add(
            userEngagementUseCase.getUserData(fromDate, toDate, status)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }.subscribe({ response ->
                    mutableLiveData.value = response
                },
                    { t: Throwable? ->
                        Log.d("Error ", t?.printStackTrace().toString())
                    }
                )
        )
    }

    fun consumeUserListByEngageStatus(): MutableLiveData<MutableList<String>> {
        return mutableLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
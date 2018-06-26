package com.bignerdranch.android.nerdmart.model.service

import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface
import com.bignerdranch.android.nerdmartservice.service.payload.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NerdMartServiceManager(var mServiceInterface : NerdMartServiceInterface) {

    fun authenticate(username: String, password: String) : Observable<Boolean> {
        return mServiceInterface.authenticate(username, password)
                .map { it != User.NO_USER }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
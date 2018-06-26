package com.bignerdranch.android.nerdmart.model.service

import com.bignerdranch.android.nerdmart.model.DataStore
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface
import com.bignerdranch.android.nerdmartservice.service.payload.Cart
import com.bignerdranch.android.nerdmartservice.service.payload.Product
import com.bignerdranch.android.nerdmartservice.service.payload.User
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class NerdMartServiceManager(var mServiceInterface : NerdMartServiceInterface,
                             var mDataStore : DataStore
) {

//    private val mSchedulersTransformer = { observable : Observable<Any> ->
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//    }
//
//    private fun <T> applySchedulers(): ObservableTransformer<T, T> {
//        return mSchedulersTransformer as ObservableTransformer<T, T>
//    }

    fun authenticate(username: String, password: String) : Observable<Boolean> {
        return mServiceInterface.authenticate(username, password)
                .doOnNext{ mDataStore.mCachedUser = it }
                .map { it != User.NO_USER }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getToken() : Observable<UUID> {
        return Observable.just(mDataStore.getCachedAuthToken())
    }

    fun getProducts() : Observable<Product> {
        return getToken()
                .flatMap{ mServiceInterface.requestProducts(it) }
                .doOnNext{ mDataStore.mCachedProducts = it }
                .flatMap{ Observable.fromIterable(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCart() : Observable<Cart> {
        return getToken()
                .flatMap { mServiceInterface.fetchUserCart(it) }
                .doOnNext{ mDataStore.mCachedCart = it }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun postProductToCart(product: Product) : Observable<Boolean> {
        return getToken()
                .flatMap { mServiceInterface.addProductToCart(it, product) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun signout() : Observable<Boolean> {
        mDataStore.clearCache()

        return mServiceInterface.signout()
    }

}
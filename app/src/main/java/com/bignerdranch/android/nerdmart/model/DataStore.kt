package com.bignerdranch.android.nerdmart.model

import com.bignerdranch.android.nerdmartservice.service.payload.Cart
import com.bignerdranch.android.nerdmartservice.service.payload.Product
import com.bignerdranch.android.nerdmartservice.service.payload.User
import java.util.*
import kotlin.collections.ArrayList

class DataStore {
    var mCachedUser : User? = null
    var mCachedProducts : List<Product>? = null
    var mCachedCart : Cart? = null


    fun getCachedCart() : Cart? {
        return mCachedCart
    }

    fun getCachedUser() : User? {
        return mCachedUser
    }

    fun clearCache() {
        mCachedProducts = ArrayList()
        mCachedCart = null
        mCachedUser = null
    }

    fun getCachedAuthToken() : UUID? {
        return mCachedUser?.authToken
    }
}
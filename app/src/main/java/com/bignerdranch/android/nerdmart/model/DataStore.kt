package com.bignerdranch.android.nerdmart.model

import com.bignerdranch.android.nerdmartservice.service.payload.Product
import com.bignerdranch.android.nerdmartservice.service.payload.User
import java.util.*

class DataStore {
    var mCachedUser : User? = null
    var mCachedProducts : List<Product>? = null

    fun getCachedAuthToken() : UUID? {
        return mCachedUser?.authToken
    }
}
package com.bignerdranch.android.nerdmart.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.bignerdranch.android.nerdmart.BR
import com.bignerdranch.android.nerdmart.R
import com.bignerdranch.android.nerdmartservice.service.payload.Cart
import com.bignerdranch.android.nerdmartservice.service.payload.User

class NerdMartViewModel(private val mContext: Context,
                        private var mCart: Cart?,
                        private val mUser: User
) : BaseObservable() {
    val userGreeting: String
        get() = mContext.getString(R.string.user_greeting, mUser.name)

    val cartDisplay: String
        @Bindable get() {
            return formatCartItemsDisplay()
        }

    fun formatCartItemsDisplay(): String {
        var numItems = 0
        if (mCart != null && mCart!!.products != null) {
            numItems = mCart!!.products.size
        }
        return mContext.getResources().getQuantityString(R.plurals.cart,
                numItems, numItems)
    }

    fun updateCartStatus(cart: Cart) {
        mCart = cart
        notifyPropertyChanged(BR._all)
    }
}
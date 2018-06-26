package com.bignerdranch.android.nerdmart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import timber.log.Timber

class ProductsFragment : NerdMartAbstractFragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        updateUi()
        return view
    }

    fun updateUi() {
        addDisposable(mNerdMartServiceManager
                .getProducts()
                .compose(loadingTransformer())
                .toList()
                .subscribe(
                        { products -> Timber.i("recieved products: " + products) },
                        { error -> Timber.i("recieved error: " + error) }
                )
        )
    }
}

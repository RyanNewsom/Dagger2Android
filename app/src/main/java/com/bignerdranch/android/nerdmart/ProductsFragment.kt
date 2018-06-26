package com.bignerdranch.android.nerdmart

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.nerdmart.databinding.FragmentProductsBinding
import com.bignerdranch.android.nerdmartservice.service.payload.Product

import timber.log.Timber
import io.reactivex.schedulers.Schedulers
import android.widget.Toast
import io.reactivex.disposables.Disposable



class ProductsFragment : NerdMartAbstractFragment(), AddProductClickEvent {
    lateinit var mFragmentProductsBinding: FragmentProductsBinding
    lateinit var mAdapter: ProductRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mFragmentProductsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        mAdapter = ProductRecyclerViewAdapter(listOf(), context!!, this)
        setupAdapter()
        updateUi()


        return mFragmentProductsBinding.root
    }

    fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(context)
        mFragmentProductsBinding.fragmentProductsRecyclerView.layoutManager = linearLayoutManager
        mFragmentProductsBinding.fragmentProductsRecyclerView.adapter = mAdapter

    }

    fun updateUi() {
        addDisposable(mNerdMartServiceManager
                .getProducts()
                .compose(loadingTransformer())
                .toList()
                .subscribe(
                        {
                            products -> Timber.i("recieved products: " + products)
                            mAdapter.mProducts = products
                            mAdapter.notifyDataSetChanged()
                        },
                        { error -> Timber.i("recieved error: " + error) }
                )
        )
    }

    override fun onProductAddClick(product: Product) {
        Timber.i("product clicked: " + product.title)
        val cartSuccessObservable = mNerdMartServiceManager
                .postProductToCart(product)
                .compose(loadingTransformer())
                .cache()
        val cartUpdateNotificationObservable = cartSuccessObservable
                .subscribe { aBoolean ->
                    val message = if (aBoolean)
                        R.string.product_add_success_message
                    else
                        R.string.product_add_failure_message
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
        addDisposable(cartUpdateNotificationObservable)
        addDisposable(cartSuccessObservable.filter { aBoolean -> aBoolean }
                .subscribeOn(Schedulers.newThread())
                .flatMap { aBoolean -> mNerdMartServiceManager.getCart() }
                .subscribe { cart ->
                    (activity as NerdMartAbstractActivity).updateCartStatus(cart)
                    updateUi()
                })
    }
}

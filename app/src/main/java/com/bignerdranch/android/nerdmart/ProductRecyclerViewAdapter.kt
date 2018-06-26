package com.bignerdranch.android.nerdmart

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bignerdranch.android.nerdmart.databinding.ViewProductRowBinding
import com.bignerdranch.android.nerdmart.viewmodel.ProductViewModel
import com.bignerdranch.android.nerdmartservice.service.payload.Product

open class ProductRecyclerViewAdapter(var mProducts : List<Product>?,
                                      val context: Context,
                                      val addProductClickEvent : AddProductClickEvent
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val viewProductRowBinding = DataBindingUtil.inflate<ViewProductRowBinding>(layoutInflater, R.layout.view_product_row, parent, false)

        return ProductViewHolder(viewProductRowBinding, addProductClickEvent, context)
    }

    override fun getItemCount(): Int {
        return mProducts?.size ?: -1
    }

    override fun onBindViewHolder(holder: ProductViewHolder?, position: Int) {
        val product = mProducts?.get(position)
        holder?.bindHolder(product, position)
    }

    class ProductViewHolder(private val mDataBinding: ViewProductRowBinding,
                            private val addProductClickEvent: AddProductClickEvent,
                            private val context: Context) : RecyclerView.ViewHolder(mDataBinding.root) {

        fun bindHolder(product: Product?, position : Int) {
            mDataBinding.productViewModel = ProductViewModel(context, product!!, position)
            mDataBinding.setBuyButtonClickListener {
                if (product != null) {
                    addProductClickEvent.onProductAddClick(product)
                }
            }
        }

    }
}

interface AddProductClickEvent {
    fun onProductAddClick(product: Product);
}


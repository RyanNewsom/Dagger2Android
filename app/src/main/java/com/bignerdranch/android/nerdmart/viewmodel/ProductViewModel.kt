package com.bignerdranch.android.nerdmart.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import com.bignerdranch.android.nerdmart.R
import com.bignerdranch.android.nerdmartservice.service.payload.Product
import java.text.NumberFormat

class ProductViewModel(var context : Context,
                       var product : Product,
                       var rowNumber : Int
) : BaseObservable() {
    val sku: String
        get() {
            return product.sku
        }

    val title: String
        get() {
            return product.title
        }

    val description: String
        get() {
            return product.description
        }
    val displayPrice : String
        get() {
            return NumberFormat.getCurrencyInstance().format(
                    product.priceInCents / 100.0
            )
        }
    val productUrl : String
        get() {
            return product.productUrl
        }
    val productionQuantityDisplay : String
        get() {
            return context.getString(R.string.quantity_display_text, product.backendQuantity)
        }

    val rowColor : Int
        get (){
        val resourceId = if(rowNumber % 2 == 0) {
            R.color.white
        } else {
            R.color.light_blue
        }
        return context.resources.getColor(resourceId)
    }
}
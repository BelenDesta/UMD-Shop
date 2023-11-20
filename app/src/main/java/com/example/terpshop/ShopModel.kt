package com.example.terpshop

import android.text.Editable
class ShopModel {

    fun getItemList(): Editable? {
        return ShoppingDetailsActivity().itemName.text
    }

    fun getCustomerName(): Editable? {
        return ContactInfoActivity().name.text
    }

    fun getCustomerAddress(): Editable? {
        return ContactInfoActivity().address.text
    }

    fun getDeliveryPrice() {}

}
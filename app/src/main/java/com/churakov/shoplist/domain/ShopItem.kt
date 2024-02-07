package com.churakov.shoplist.domain

data class ShopItem(
    val value: String,
    val amount: String,
    val signed: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}

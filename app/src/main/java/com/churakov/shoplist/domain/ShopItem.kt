package com.churakov.shoplist.domain

data class ShopItem(
    val id: Int,
    val value: String,
    val amount: String,
    val signed: Boolean
)

package com.churakov.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)
    fun removeShopItem(id: Int)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}
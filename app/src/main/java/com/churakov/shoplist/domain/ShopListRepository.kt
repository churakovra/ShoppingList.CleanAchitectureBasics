package com.churakov.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun removeShopItem(id: Int)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}
package com.churakov.shoplist.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)
    fun removeShopItem(id: Int)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun getShopList(): List<ShopItem>
}
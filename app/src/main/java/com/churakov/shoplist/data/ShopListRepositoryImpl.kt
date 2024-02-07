package com.churakov.shoplist.data

import com.churakov.shoplist.domain.ShopItem
import com.churakov.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shoppingList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0..< 20){
            addShopItem(ShopItem("v$i", "$i", true))
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shoppingList.add(shopItem)
    }

    override fun removeShopItem(id: Int) {
        shoppingList.removeAt(id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shoppingList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shoppingList.find {
            it.id == id
        } ?: throw RuntimeException("item with id $id is not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shoppingList.toList()
    }
}
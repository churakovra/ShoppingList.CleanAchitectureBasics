package com.churakov.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.churakov.shoplist.domain.ShopItem
import com.churakov.shoplist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shoppingListLD = MutableLiveData<List<ShopItem>>()
    private val shoppingList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            addShopItem(ShopItem("value$i", "$i", Random.nextBoolean()))
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shoppingList.add(shopItem)
        updateList()
    }

    override fun removeShopItem(id: Int) {
        shoppingList.removeAt(id)
        updateList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shoppingListLD
    }

    private fun updateList() {
        shoppingListLD.value = shoppingList.toList()
    }
}
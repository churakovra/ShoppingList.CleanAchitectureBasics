package com.churakov.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.churakov.shoplist.domain.ShopItem
import com.churakov.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shoppingList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    private val shopListLD = MutableLiveData<List<ShopItem>>()

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
        updateShopListLDState()
    }

    override fun removeShopItem(id: Int) {
        val item = getShopItem(id)
        shoppingList.remove(item)
        updateShopListLDState()
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
        return shopListLD
    }

    private fun updateShopListLDState(){
        shopListLD.value = shoppingList.toList()
    }
}
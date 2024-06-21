package com.churakov.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopListUseCase
import com.churakov.shoplist.domain.RemoveShopItemUseCase
import com.churakov.shoplist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val editedShopItem = shopItem.copy(signed = !shopItem.signed)
        editShopItemUseCase.editShopItem(editedShopItem)
    }
}
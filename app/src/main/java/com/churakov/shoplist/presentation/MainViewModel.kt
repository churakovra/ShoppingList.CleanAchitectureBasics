package com.churakov.shoplist.presentation

import androidx.lifecycle.MutableLiveData
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

    val shopList = MutableLiveData<List<ShopItem>>()


    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun removeShopItem(shopItemId: Int) {
        removeShopItemUseCase.removeShopItem(shopItemId)
        getShopList()
    }

    fun changeEnableState(shopItem: ShopItem) {
        val editedShopItem = shopItem.copy(signed = !shopItem.signed)
        editShopItemUseCase.editShopItem(editedShopItem)
        getShopList()
    }
}
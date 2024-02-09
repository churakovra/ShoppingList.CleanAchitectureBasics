package com.churakov.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopListUseCase
import com.churakov.shoplist.domain.RemoveShopItemUseCase
import com.churakov.shoplist.domain.ShopItem


// not AndroidViewModel(application) because context is not necessary
class MainViewModel : ViewModel() {

    // incorrect way. Should use dependency injection
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopListChanged = getShopListUseCase.getShopList()

    fun removeShopItem(id: Int){
        removeShopItemUseCase.removeShopItem(id)
    }

    fun changeEnableState(item: ShopItem){
        val newShopItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}
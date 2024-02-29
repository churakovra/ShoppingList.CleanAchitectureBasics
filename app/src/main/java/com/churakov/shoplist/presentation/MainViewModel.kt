package com.churakov.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopListUseCase
import com.churakov.shoplist.domain.RemoveShopItemUseCase
import com.churakov.shoplist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


// not AndroidViewModel(application) because context is not necessary
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // incorrect way. Should use dependency injection
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopListChanged = getShopListUseCase.getShopList()

    fun removeShopItem(id: Int){
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(id)
        }

    }

    fun changeEnableState(item: ShopItem){
        viewModelScope.launch {
            val newShopItem = item.copy(enabled = !item.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }

    }
}
package com.churakov.shoplist.presentation

import androidx.lifecycle.MutableLiveData
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

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun removeShopItem(id: Int){
        removeShopItemUseCase.removeShopItem(id)
        getShopList()
    }

    fun editShopItem(item: ShopItem){
        val newShopItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
        getShopList()
    }
}
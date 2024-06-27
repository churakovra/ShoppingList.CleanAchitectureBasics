package com.churakov.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.AddShopItemUseCase
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopItemUseCase
import com.churakov.shoplist.domain.ShopItem

class EditShopItemViewModel: ViewModel() {
    private val shopListRepository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    private val _saveShopItem = MutableLiveData<Boolean>()
    val saveShopItem: LiveData<Boolean> = _saveShopItem

    private val _getShopItem = MutableLiveData<ShopItem>()
    val getShopItem: LiveData<ShopItem> = _getShopItem

    init {
        _saveShopItem.value = false
    }

    fun saveShopItemAddMode(shopItem: ShopItem){
        addShopItemUseCase.addShopItem(shopItem)
        _saveShopItem.value = true
    }

    fun saveShopItemEditMode(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
        _saveShopItem.value = true
    }

    /*fun saveShopItem(shopItem: ShopItem, mode: String) {
        when(mode) {
            MODE_ADD -> {
                addShopItemUseCase.addShopItem(shopItem)
                _saveShopItem.value = true
            }
            MODE_EDIT -> {
                editShopItemUseCase.editShopItem(shopItem)
                _saveShopItem.value = true
            }
        }
    }*/

    fun getShopItem(shopItemId: Int){
        val shopItem = getShopItemUseCase.getShopItem(shopItemId)
        _getShopItem.value = shopItem
    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
    }

}
package com.churakov.shoplist.presentation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.AddShopItemUseCase
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopItemUseCase
import com.churakov.shoplist.domain.ShopItem

class EditShopItemViewModel(private val application: Application) : AndroidViewModel(application) {
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

    fun saveShopItemAddMode(value: String, amount: String) {
        if (validateInput(value, amount)) {
            val shopItem = ShopItem(value, amount.toInt(), true)
            addShopItemUseCase.addShopItem(shopItem)
            _saveShopItem.value = true
        }
    }

    fun saveShopItemEditMode(currentShopItem: ShopItem, value: String, amount: String) {
        if (validateInput(value, amount)) {
            val shopItem = currentShopItem.copy(value = value, amount = amount.toInt())
            editShopItemUseCase.editShopItem(shopItem)
            _saveShopItem.value = true
        }
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

    fun getShopItem(shopItemId: Int) {
        val shopItem = getShopItemUseCase.getShopItem(shopItemId)
        _getShopItem.value = shopItem
    }

    private fun validateInput(label: String, value: String): Boolean {
        if (label.isEmpty() || value.isEmpty()) {
            Toast.makeText(
                application.applicationContext,
                "Fields is empty",
                Toast.LENGTH_SHORT
            ).show()
        }
        return !(label.isEmpty() || value.isEmpty())
    }

}
package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.churakov.shoplist.data.ShopListRepositoryImpl
import com.churakov.shoplist.domain.AddShopItemUseCase
import com.churakov.shoplist.domain.EditShopItemUseCase
import com.churakov.shoplist.domain.GetShopItemUseCase
import com.churakov.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repositoryImpl = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(repositoryImpl)
    private val getShopItemUseCase = GetShopItemUseCase(repositoryImpl)

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem
    private val _ableToCloseScreen = MutableLiveData<Unit>()
    val ableToCloseScreen: LiveData<Unit>
        get() = _ableToCloseScreen

    private val _errorInputValue = MutableLiveData<Boolean>()
    val errorInputValue: LiveData<Boolean>
        get() = _errorInputValue

    private val _errorInputAmount = MutableLiveData<Boolean>()
    val errorInputAmount: LiveData<Boolean>
        get() = _errorInputAmount

    fun addShopItem(itemValue: String?, itemAmount: String?) {
        val value = parseValue(itemValue)
        val amount = parseAmount(itemAmount)
        val fieldsValid = validateInput(value, amount)
        if (fieldsValid) {
            val shopItem = ShopItem(value, amount.toString(), true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(itemValue: String?, itemAmount: String?) {
        val value = parseValue(itemValue)
        val amount = parseAmount(itemAmount)
        val fieldsValid = validateInput(value, amount)
        if (fieldsValid) {
            _shopItem.value?.let {
                val oldShopItem = it
                val (_, _, enabled, id) = oldShopItem
                val newShopItem = ShopItem(value, amount.toString(), enabled, id)
                editShopItemUseCase.editShopItem(newShopItem)
                finishWork()
            }
        }
    }

    fun getShopItem(id: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(id)
    }

    private fun parseValue(value: String?): String {
        return value?.trim() ?: ""
    }

    private fun parseAmount(amount: String?): Int {
        return try {
            amount?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun validateInput(value: String, amount: Int): Boolean {
        if (value.isBlank()) {
            _errorInputValue.value = true
            return false
        }
        if (amount <= 0) {
            _errorInputAmount.value = true
            return false
        }
        return true
    }

    private fun finishWork() {
        _ableToCloseScreen.value = Unit
    }

    fun resetErrorInputValue() {
        _errorInputValue.value = false
    }

    fun resetErrorInputAmount() {
        _errorInputAmount.value = false
    }
}
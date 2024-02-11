package com.churakov.shoplist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun removeShopItem(id: Int) {
        shopListRepository.removeShopItem(id)
    }
}
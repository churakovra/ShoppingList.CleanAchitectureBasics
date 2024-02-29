package com.churakov.shoplist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}
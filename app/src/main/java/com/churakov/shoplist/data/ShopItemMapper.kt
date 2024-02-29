package com.churakov.shoplist.data

import com.churakov.shoplist.domain.ShopItem

class ShopItemMapper {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
            shopItem.id,
            shopItem.value,
            shopItem.amount,
            shopItem.enabled
        )


    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
            shopItemDbModel.value,
            shopItemDbModel.amount,
            shopItemDbModel.enabled,
            shopItemDbModel.id
        )


    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map { mapDbModelToEntity(it) }

}
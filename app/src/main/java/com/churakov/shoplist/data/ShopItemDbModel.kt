package com.churakov.shoplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.churakov.shoplist.domain.ShopItem

@Entity("shop_item")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val value: String,
    val amount: String,
    val enabled: Boolean,
)

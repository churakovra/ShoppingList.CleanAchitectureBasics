package com.churakov.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_item")
    fun getItemList(): LiveData<List<ShopItemDbModel>>
    @Query("SELECT * FROM shop_item WHERE id = :id")
    fun getItem(id: Int): LiveData<ShopItemDbModel>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(item: ShopItemDbModel)
    @Query("DELETE FROM shop_item WHERE id = :id LIMIT 1")
    fun removeItem(id: Int)

}
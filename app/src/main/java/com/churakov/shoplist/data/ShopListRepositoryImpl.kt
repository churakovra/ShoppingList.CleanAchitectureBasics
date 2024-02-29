package com.churakov.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.churakov.shoplist.domain.ShopItem
import com.churakov.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = ShopListDatabase.getInstance(application).shopListDao()
    private val mapper = ShopItemMapper()

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun removeShopItem(id: Int) {
        shopListDao.removeItem(id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(id: Int): ShopItem {
        val dbModel = shopListDao.getItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getItemList().map {
        mapper.mapListDbModelToListEntity(it)
    }
}
package com.churakov.shoplist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShopListDatabase: RoomDatabase() {

    abstract fun shopListDao() : ShopListDao

    companion object {

        private var INSTANCE: ShopListDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "shop_list.db"

        fun getInstance(application: Application): ShopListDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application.applicationContext,
                    ShopListDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
                INSTANCE = db
                return db
            }
        }
    }
}
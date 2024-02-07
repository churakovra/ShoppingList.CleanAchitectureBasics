package com.churakov.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            Log.d(TAG, it.toString())
        }
        viewModel.getShopList()

        viewModel.removeShopItem(0)
        viewModel.editShopItem(ShopItem("v1", "1", true, 1))
    }

    companion object{
        private const val TAG = "GET_SHOP_LIST_FROM_VIEW_MODEL"
    }
}
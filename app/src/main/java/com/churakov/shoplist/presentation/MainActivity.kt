package com.churakov.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListChanged.observe(this){
            shopListAdapter.shopList = it
            Log.d(TAG, it.toString())
        }


    }

    private fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.shopListRecyclerView)
        shopListAdapter = ShopListAdapter()
        rvShopList.adapter = shopListAdapter
        //change recycler view pool max size
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
    }

    companion object{
        private const val TAG = "GET_SHOP_LIST_FROM_VIEW_MODEL"
    }
}
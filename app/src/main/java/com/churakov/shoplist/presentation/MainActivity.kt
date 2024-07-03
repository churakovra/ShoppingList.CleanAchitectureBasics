package com.churakov.shoplist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), EditShopItemFragment.OnEditShopItemFinished {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var floatingButton: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shopItemFragmentContainer)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        floatingButton = findViewById(R.id.addShopItemFB)
        floatingButton.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = EditShopItemActivity.newIntentScreenAdd(this)
                startActivity(intent)
            } else {
                launchFragment(EditShopItemFragment.getInstanceAddMode())
            }
        }
    }

    override fun onEditShopItemFinished() {
        onEditFinished()
    }

    private fun onEditFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.shopListRV)
        setupRvAdapter(rvShopList)
        setupRvClickListeners()
        setupItemTouchHelper(rvShopList)

    }

    private fun setupRvClickListeners() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = EditShopItemActivity.newIntentScreenEdit(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(EditShopItemFragment.getInstanceEditMode(it.id))
            }
        }
    }

    private fun setupRvAdapter(rvShopList: RecyclerView) {
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.SHOP_ITEM_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.SHOP_ITEM_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setupItemTouchHelper(rv: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(
                    0,
                    ItemTouchHelper.START or ItemTouchHelper.END
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item)
            }

        })
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun isOnePaneMode(): Boolean = shopItemContainer == null

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shopItemFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
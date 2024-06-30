package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class EditShopItemActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_shop_item_activity)

        fragmentContainer = findViewById(R.id.editShopItemContainerView)
        launchScreen()
    }

    private fun launchScreen() {
        val launchMode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        when (launchMode) {
            MODE_ADD -> {
                launchAddMode()
            }

            MODE_EDIT -> {
                launchEditMode()
            }
        }
    }

    private fun launchAddMode() {
        val fragment = EditShopItemFragment.getInstanceAddMode()
        supportFragmentManager.beginTransaction()
            .add(R.id.editShopItemContainerView, fragment)
            .commit()
    }

    private fun launchEditMode() {
        val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        val fragment = EditShopItemFragment.getInstanceEditMode(shopItemId)
        supportFragmentManager.beginTransaction()
            .add(R.id.editShopItemContainerView, fragment)
            .commit()
    }


    companion object {
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentScreenAdd(context: Context): Intent {
            val intent = Intent(context, EditShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentScreenEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, EditShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}
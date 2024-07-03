package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class EditShopItemActivity : AppCompatActivity(), EditShopItemFragment.OnEditShopItemFinished {

    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_shop_item_activity)

        fragmentContainer = findViewById(R.id.editShopItemContainerView)
        if (isFirstRun(savedInstanceState)) {
            launchScreen()
        }
    }

    override fun onEditShopItemFinished() {
        onEditFinished()
    }

    private fun onEditFinished() {
        finish()
    }

    private fun isFirstRun(bundle: Bundle?): Boolean = bundle == null

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
        supportFragmentManager.popBackStack()
        val fragment = EditShopItemFragment.getInstanceAddMode()
        supportFragmentManager.beginTransaction()
            .replace(R.id.editShopItemContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchEditMode() {
        supportFragmentManager.popBackStack()
        val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        val fragment = EditShopItemFragment.getInstanceEditMode(shopItemId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.editShopItemContainerView, fragment)
            .addToBackStack(null)
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
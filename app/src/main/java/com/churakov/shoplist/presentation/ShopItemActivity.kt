package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditFinishedListener {

    private var itemId = ShopItem.UNDEFINED_ID
    private var screenMode = SCREEN_MODE_UNDEFINED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null){
            launchCorrectMode()
        }
    }

    override fun onEditFinished() {
        Toast.makeText(
            this,
            "Success!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun launchCorrectMode() {
        val fragment: ShopItemFragment = when (screenMode) {
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEdit(itemId)

            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAdd()

            else -> {
                throw RuntimeException("Screen mode is unknown")
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemFragmentContainer, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE_TYPE)) {
            throw RuntimeException("Intent has no screen mode")
        }
        val mode = intent.getStringExtra(EXTRA_MODE_TYPE)
        if (mode != EXTRA_MODE_EDIT && mode != EXTRA_MODE_ADD) {
            throw RuntimeException("Screen mode is unknown")
        }
        screenMode = mode

        if (screenMode == EXTRA_MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Screen mode is edit & item_id is undefined")
            }
            itemId = intent.getIntExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        fun newIntentEditItem(
            context: Context,
            itemId: Int
        ): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_TYPE, EXTRA_MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, itemId)
            return intent
        }

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_TYPE, EXTRA_MODE_ADD)
            return intent
        }

        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val EXTRA_MODE_TYPE = "extra_mode_type"
        private const val EXTRA_MODE_ADD = "extra_mode_add"
        private const val EXTRA_MODE_EDIT = "extra_mode_edit"

        private const val SCREEN_MODE_UNDEFINED = ""

    }
}
package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private var itemId = ShopItem.UNDEFINED_ID
    private var screenMode = SCREEN_MODE_UNDEFINED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        launchCorrectMode()


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
            .add(R.id.shopItemFragmentContainer, fragment)
            .commit()
    }

    //
//    private fun setupEditMode() {
//        observeShopItem()
//        setupButtonClick(screenMode)
//    }
//
//    private fun setupAddMode() {
//        setupButtonClick(screenMode)
//    }
//
//    private fun observeShopItem() {
//        shopItemViewModel.getShopItem(itemId)
//        shopItemViewModel.shopItem.observe(this) {
//            valueTextInput.setText(it.value)
//            amountTextInput.setText(it.amount)
//        }
//    }
//
//    private fun setupErrorInputValue() {
//        shopItemViewModel.errorInputValue.observe(this) {
//            val message = if (it) {
//                getString(R.string.value_shop_item_error_message)
//            } else {
//                null
//            }
//            valueTextInputLayout.error = message
//        }
//    }
//
//    private fun setupErrorInputAmount() {
//        shopItemViewModel.errorInputAmount.observe(this) {
//            val message = if (it) {
//                getString(R.string.amount_shop_item_error_message)
//            } else {
//                null
//            }
//            amountTextInputLayout.error = message
//        }
//    }
//
//    private fun resetValueInputError() {
//        valueTextInput.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                shopItemViewModel.resetErrorInputValue()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//    private fun resetAmountInputError() {
//        amountTextInput.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                shopItemViewModel.resetErrorInputAmount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//    private fun setupCloseScreenObserver() {
//        shopItemViewModel.ableToCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
//    private fun setupButtonClick(mode: String) {
//        shopItemSaveButton.setOnClickListener {
//            val itemValue = valueTextInput.text?.toString()
//            val itemAmount = amountTextInput.text?.toString()
//
//            Log.d(TAG, "Item values: $itemValue, $itemAmount")
//
//            when (mode) {
//                EXTRA_MODE_ADD -> shopItemViewModel.addShopItem(itemValue, itemAmount)
//                EXTRA_MODE_EDIT -> shopItemViewModel.editShopItem(itemValue, itemAmount)
//            }
//        }
//    }
//
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
//
//    private fun initViews() {
//        valueTextInput = findViewById(R.id.valueTextInput)
//        amountTextInput = findViewById(R.id.amountTextInput)
//        shopItemSaveButton = findViewById(R.id.shopItemSaveButton)
//        valueTextInputLayout = findViewById(R.id.valueTextInputLayout)
//        amountTextInputLayout = findViewById(R.id.amountTextInputLayout)
//    }

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

        private const val TAG = "ShopItemActivity"
        private const val SCREEN_MODE_UNDEFINED = ""

    }
}
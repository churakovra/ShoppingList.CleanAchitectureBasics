package com.churakov.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class EditShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: EditShopItemViewModel
    private lateinit var labelEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var currentShopItem: ShopItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_shop_item_activity)
        initViews()
        observeViewModel()
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
        saveButton.setOnClickListener {
            if (labelEditText.text.isEmpty() || amountEditText.text.isEmpty()) {
                Toast.makeText(this, "Please, enter values", Toast.LENGTH_SHORT).show()
            } else {
                val (label, value) = getNewValues()
                val shopItem = ShopItem(label, value, true)
                viewModel.saveShopItemAddMode(shopItem)
            }
        }
    }

    private fun launchEditMode() {
        val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        if (shopItemId == ShopItem.UNDEFINED_ID) {
            Toast.makeText(this, "Wrong shopItemId", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.getShopItem(shopItemId)
        saveButton.setOnClickListener {
            val (value, amount) = getNewValues()
            val shopItem = currentShopItem.copy(value = value, amount = amount)
            viewModel.saveShopItemEditMode(shopItem)
        }

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[EditShopItemViewModel::class.java]

        viewModel.getShopItem.observe(this) {
            currentShopItem = it
            labelEditText.setText(it.value)
            amountEditText.setText(it.amount.toString())
        }

        viewModel.saveShopItem.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    private fun getNewValues(): Pair<String, Int> {
        val label = labelEditText.text.trim().toString()
        val amount = amountEditText.text.trim().toString().toInt()
        return Pair(label, amount)
    }

    private fun initViews() {
        labelEditText = findViewById(R.id.shopItemLabelEt)
        amountEditText = findViewById(R.id.shopItemAmountEt)
        saveButton = findViewById(R.id.saveShopItemBt)
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
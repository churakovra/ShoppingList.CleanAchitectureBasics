package com.churakov.shoplist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class EditShopItemFragment : Fragment() {

    private lateinit var viewModel: EditShopItemViewModel
    private lateinit var labelEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var currentShopItem: ShopItem

    private var screenMode: String = UNKNOWN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_shop_item_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        observeViewModel()
        launchScreen()
    }

    private fun parseArgs() {
        val args = arguments ?: throw RuntimeException("Invalid arguments")
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Screen mode is absent")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id is absent")
            }
            shopItemId = ShopItem.UNDEFINED_ID
        }
        shopItemId = args.getInt(SHOP_ITEM_ID)
    }

    private fun launchScreen() {
        when (screenMode) {
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
            val (value, amount) = getNewValues()
            viewModel.saveShopItemAddMode(value, amount)
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        saveButton.setOnClickListener {
            val (value, amount) = getNewValues()
            viewModel.saveShopItemEditMode(currentShopItem, value, amount)
        }

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[EditShopItemViewModel::class.java]

        viewModel.getShopItem.observe(viewLifecycleOwner) {
            currentShopItem = it
            labelEditText.setText(it.value)
            amountEditText.setText(it.amount.toString())
        }

        viewModel.saveShopItem.observe(viewLifecycleOwner) {
            if (it) {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun getNewValues(): Pair<String, String> {
        val value = labelEditText.text.trim().toString()
        val amount = amountEditText.text.trim().toString()
        return Pair(value, amount)
    }

    private fun initViews(view: View) {
        labelEditText = view.findViewById(R.id.shopItemLabelEt)
        amountEditText = view.findViewById(R.id.shopItemAmountEt)
        saveButton = view.findViewById(R.id.saveShopItemBt)
    }

    companion object {
        fun getInstanceAddMode(): EditShopItemFragment {
            return EditShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun getInstanceEditMode(shopItemId: Int): EditShopItemFragment {
            return EditShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val UNKNOWN_MODE = "unknown_mode"
    }
}
package com.churakov.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var valueTextInputLayout: TextInputLayout
    private lateinit var amountTextInputLayout: TextInputLayout
    private lateinit var valueTextInput: TextInputEditText
    private lateinit var amountTextInput: TextInputEditText
    private lateinit var shopItemSaveButton: Button
    private lateinit var shopItemViewModel: ShopItemViewModel


    private var screenMode: String = SCREEN_MODE_UNDEFINED
    private var itemId: Int  = ShopItem.UNDEFINED_ID

    private lateinit var onEditFinishedListener: OnEditFinishedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditFinishedListener) {
            onEditFinishedListener = context
        } else throw RuntimeException("Activity mus implement OnEditFinishedListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        launchCorrectMode()
        setupErrorInputValue()
        setupErrorInputAmount()
        resetValueInputError()
        resetAmountInputError()
        setupCloseScreenObserver()

    }

    private fun launchCorrectMode() {
        when (screenMode) {
            EXTRA_MODE_ADD -> setupAddMode()
            EXTRA_MODE_EDIT -> setupEditMode()
        }
    }

    private fun setupEditMode() {
        observeShopItem()
        setupButtonClick(screenMode)
    }

    private fun setupAddMode() {
        setupButtonClick(screenMode)
    }

    private fun observeShopItem() {
        shopItemViewModel.getShopItem(itemId)
        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            valueTextInput.setText(it.value)
            amountTextInput.setText(it.amount)
        }
    }

    private fun setupErrorInputValue() {
        shopItemViewModel.errorInputValue.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.value_shop_item_error_message)
            } else {
                null
            }
            valueTextInputLayout.error = message
        }
    }

    private fun setupErrorInputAmount() {
        shopItemViewModel.errorInputAmount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.amount_shop_item_error_message)
            } else {
                null
            }
            amountTextInputLayout.error = message
        }
    }

    private fun resetValueInputError() {
        valueTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputValue()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun resetAmountInputError() {
        amountTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputAmount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupCloseScreenObserver() {
        shopItemViewModel.ableToCloseScreen.observe(viewLifecycleOwner) {
            onEditFinishedListener.onEditFinished()
        }
    }

    private fun setupButtonClick(mode: String) {
        shopItemSaveButton.setOnClickListener {
            val itemValue = valueTextInput.text?.toString()
            val itemAmount = amountTextInput.text?.toString()

            Log.d(TAG, "Item values: $itemValue, $itemAmount")

            when (mode) {
                EXTRA_MODE_ADD -> shopItemViewModel.addShopItem(itemValue, itemAmount)
                EXTRA_MODE_EDIT -> shopItemViewModel.editShopItem(itemValue, itemAmount)
            }
        }
    }

    private fun parseParams() {
        val args = arguments
        if (args?.containsKey(EXTRA_MODE_TYPE) == false) {
            throw RuntimeException("Intent has no screen mode")
        }
        val mode = args?.getString(EXTRA_MODE_TYPE)
        if (mode != EXTRA_MODE_EDIT && mode != EXTRA_MODE_ADD) {
            throw RuntimeException("Screen mode is unknown")
        }
        screenMode = mode
        if (screenMode == EXTRA_MODE_EDIT) {
            if (!args.containsKey(EXTRA_ITEM_ID)) {
                throw RuntimeException("Screen mode is edit & item_id is undefined")
            }
            itemId = args.getInt(EXTRA_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        valueTextInput = view.findViewById(R.id.valueTextInput)
        amountTextInput = view.findViewById(R.id.amountTextInput)
        shopItemSaveButton = view.findViewById(R.id.shopItemSaveButton)
        valueTextInputLayout = view.findViewById(R.id.valueTextInputLayout)
        amountTextInputLayout = view.findViewById(R.id.amountTextInputLayout)
    }

    interface OnEditFinishedListener {
        fun onEditFinished()
    }

    companion object {

        fun newInstanceAdd(): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE_TYPE, EXTRA_MODE_ADD)
                }
            }
        }

        fun newInstanceEdit(itemId: Int): ShopItemFragment{
            return ShopItemFragment().apply{
                arguments = Bundle().apply {
                    putString(EXTRA_MODE_TYPE, EXTRA_MODE_EDIT)
                    putInt(EXTRA_ITEM_ID, itemId)
                }
            }
        }

        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val EXTRA_MODE_TYPE = "extra_mode_type"
        private const val EXTRA_MODE_ADD = "extra_mode_add"
        private const val EXTRA_MODE_EDIT = "extra_mode_edit"

        private const val TAG = "ShopItemActivity"
        private const val SCREEN_MODE_UNDEFINED = ""

    }
}
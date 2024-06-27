package com.churakov.shoplist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.churakov.shoplist.R

class EditShopItemFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_shop_item_fragment, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
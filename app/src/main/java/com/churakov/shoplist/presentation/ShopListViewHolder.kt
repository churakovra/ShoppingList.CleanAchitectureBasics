package com.churakov.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R

class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        val tvValue = itemView.findViewById<TextView>(R.id.valueItemEnabledTextView)
        val tvAmount = itemView.findViewById<TextView>(R.id.amountItemEnabledTextView)

}
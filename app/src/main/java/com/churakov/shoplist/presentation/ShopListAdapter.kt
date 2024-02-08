package com.churakov.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_list_item_enabled,
            parent,
            false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopListItem = shopList[position]
        holder.tvValue.text = shopListItem.value
        holder.tvAmount.text = shopListItem.amount

        holder.itemView.setOnLongClickListener {
            true
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopListViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvValue = itemView.findViewById<TextView>(R.id.valueItemEnabledTextView)
        val tvAmount = itemView.findViewById<TextView>(R.id.valueItemEnabledTextView)
    }
}
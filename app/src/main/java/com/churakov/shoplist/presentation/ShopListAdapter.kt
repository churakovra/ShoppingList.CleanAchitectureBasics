package com.churakov.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopListAdapter() : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var counter = 0
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_ENABLED -> R.layout.shop_item_enabled

            SHOP_ITEM_DISABLED -> R.layout.shop_item_disabled

            else -> throw RuntimeException("Shop item should be enabled or disabled")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        Log.d("ShopListAdapter", "onCreateViewHolder count: ${++counter}")
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.shopItemTitle.text = shopItem.value
        holder.shopItemCount.text = shopItem.amount.toString()
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].signed) SHOP_ITEM_ENABLED else SHOP_ITEM_DISABLED
    }

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopItemTitle: TextView = itemView.findViewById(R.id.shopItemTitle)
        val shopItemCount: TextView = itemView.findViewById(R.id.shopItemCount)
    }

    companion object {
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_POOL_SIZE = 10
    }
}

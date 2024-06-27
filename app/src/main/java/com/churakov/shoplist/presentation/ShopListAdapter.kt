package com.churakov.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopListAdapter() :
    ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {

    private var counter = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

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
        val shopItem = getItem(position)
        holder.shopItemTitle.text = shopItem.value
        holder.shopItemCount.text = shopItem.amount.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).signed) SHOP_ITEM_ENABLED else SHOP_ITEM_DISABLED
    }

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopItemTitle: TextView = itemView.findViewById(R.id.shopItemTitle)
        val shopItemCount: TextView = itemView.findViewById(R.id.shopItemAmountEt)
    }

    //java way of creating a recyclerView clickListener
    /*interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
    }
    interface OnShopItemClickListener {
        fun onShopItemClick(shopItem: ShopItem)
    }*/

    companion object {
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_POOL_SIZE = 10
    }
}

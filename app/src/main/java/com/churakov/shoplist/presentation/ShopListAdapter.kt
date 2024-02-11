package com.churakov.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_list_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_list_item_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopListItem = getItem(position)
        holder.tvValue.text = shopListItem.value
        holder.tvAmount.text = shopListItem.amount

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopListItem)
                ?: throw RuntimeException(ON_SHOP_ITEM_LONG_CLICK_LISTENER_ERROR)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopListItem)
                ?: throw RuntimeException(ON_SHOP_ITEM_CLICK_LISTENER_ERROR)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = -1

        const val MAX_POOL_SIZE = 30

        const val ON_SHOP_ITEM_LONG_CLICK_LISTENER_ERROR = "onShopItemLongClickListener == null"
        const val ON_SHOP_ITEM_CLICK_LISTENER_ERROR = "onShopItemClickListener == null"
    }
}
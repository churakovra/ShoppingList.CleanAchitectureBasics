package com.churakov.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        Log.d("adapterLog", "${count++}")

        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_list_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_list_item_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopListItem = shopList[position]
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

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    // just shows that rv has functionality to do when item recycled
    override fun onViewRecycled(holder: ShopListViewHolder) {
        holder.tvValue.text = holder.itemView.context.getString(R.string.recycled)
        holder.tvAmount.text = holder.itemView.context.getString(R.string.recycled)
    }

    class ShopListViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvValue = itemView.findViewById<TextView>(R.id.valueItemEnabledTextView)
        val tvAmount = itemView.findViewById<TextView>(R.id.amountItemEnabledTextView)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = -1

        const val MAX_POOL_SIZE = 10

        const val ON_SHOP_ITEM_LONG_CLICK_LISTENER_ERROR = "onShopItemLongClickListener == null"
        const val ON_SHOP_ITEM_CLICK_LISTENER_ERROR = "onShopItemClickListener == null"
    }
}
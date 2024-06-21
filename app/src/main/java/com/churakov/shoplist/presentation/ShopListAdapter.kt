package com.churakov.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.churakov.shoplist.R
import com.churakov.shoplist.domain.ShopItem

class ShopListAdapter(): RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var shopList = listOf<ShopItem>()

    fun setShopList(list: List<ShopItem>) {
        shopList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item_enabled, parent, false)
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

    class ShopItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val shopItemTitle: TextView = itemView.findViewById(R.id.shopItemTitle)
        val shopItemCount: TextView = itemView.findViewById(R.id.shopItemCount)
    }
}

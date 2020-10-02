package com.kimjinhwan.android.naverapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.text.NumberFormat
import java.util.*

class ResultRecyclerAdapter(private val context: Context): PagedListAdapter<ResultItem, ResultRecyclerAdapter.ResultHolder>(DiffCallBack()) {

    private var lowestPrice: Long = Long.MAX_VALUE

    class DiffCallBack: DiffUtil.ItemCallback<ResultItem>() {
        override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ResultHolder(view)
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setLowestPrice(price: Long) {
        lowestPrice = price
    }

    inner class ResultHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val numberFormat = NumberFormat.getInstance()

        fun bind(item: ResultItem) {
            Glide.with(context).load(item.image).into(itemView.itemImage)
            itemView.titleTxt.text =
                item.title
                .replace("<b>", "")
                .replace("</b>", "")
            itemView.priceTxt.text = context.getString(R.string.won, numberFormat.format(item.lprice))
            if(item.lprice == lowestPrice){
                itemView.priceTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            } else {
                itemView.priceTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("item", item)
                context.startActivity(intent)
            }
        }
    }
}
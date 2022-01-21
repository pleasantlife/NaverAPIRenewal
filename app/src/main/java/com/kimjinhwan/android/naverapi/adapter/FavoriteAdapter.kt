package com.kimjinhwan.android.naverapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.model.ResultItem
import com.kimjinhwan.android.naverapi.view.DetailActivity
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.text.NumberFormat

class FavoriteAdapter(private val context: Context): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private var itemList: List<ResultItem> = ArrayList<ResultItem>()

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberFormat = NumberFormat.getInstance()

        fun bind(item: ResultItem) {
            Glide.with(context).load(item.image).into(itemView.itemImage)
            itemView.titleTxt.text =
                item.title
                    .replace("<b>", "")
                    .replace("</b>", "")
            itemView.priceTxt.text = context.getString(R.string.won, numberFormat.format(item.lprice.toLong()))
            itemView.priceTxt.setTextColor(ContextCompat.getColor(context,
                R.color.black
            ))
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("item", item)
                context.startActivity(intent)
            }
        }
    }

    fun setListData(itemList: List<ResultItem>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
       return itemList.size
    }
}
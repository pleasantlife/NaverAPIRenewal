package com.kimjinhwan.android.naverapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimjinhwan.android.naverapi.R
import com.kimjinhwan.android.naverapi.databinding.RecyclerItemBinding
import com.kimjinhwan.android.naverapi.model.ResultItem
import com.kimjinhwan.android.naverapi.view.DetailActivity
import java.text.NumberFormat

class FavoriteAdapter(private val context: Context): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private var itemList: List<ResultItem> = ArrayList<ResultItem>()

    inner class FavoriteHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val numberFormat = NumberFormat.getInstance()

        fun bind(item: ResultItem) {
            Glide.with(context).load(item.image).into(binding.itemImage)
            binding.titleTxt.text =
                item.title
                    .replace("<b>", "")
                    .replace("</b>", "")
            binding.priceTxt.text = context.getString(R.string.won, numberFormat.format(item.lprice.toLong()))
            binding.priceTxt.setTextColor(ContextCompat.getColor(context,
                R.color.black
            ))
            binding.root.setOnClickListener {
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
        val view = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
       return itemList.size
    }
}
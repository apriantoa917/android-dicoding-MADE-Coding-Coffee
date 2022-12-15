package com.aprianto.core.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprianto.core.R
import com.aprianto.core.databinding.RvMenuBinding
import com.aprianto.core.domain.model.Menu
import com.bumptech.glide.Glide

class MenuRvAdapter : RecyclerView.Adapter<MenuRvAdapter.ListViewHolder>() {

    private var listData = ArrayList<Menu>()
    var onItemClick: ((Menu) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Menu>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_menu, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvMenuBinding.bind(itemView)
        fun bind(data: Menu) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.productImage)
                    .into(ivProductImage)
                tvProductName.text = data.productName
                labelProductCategory.tvProductCategory.text = data.productCategory
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}

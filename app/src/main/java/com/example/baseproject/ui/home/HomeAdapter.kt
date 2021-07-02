package com.example.baseproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemUserBinding
import com.example.baseproject.model.Item

class HomeAdapter(
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val items = mutableListOf<Item>()

    fun setData(items: List<Item>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: ItemUserBinding,
        private val onItemClick: (Item) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Item) {
            binding.also {
                it.item = item
                it.root.setOnClickListener {
                    onItemClick(item)
                }
                it.executePendingBindings()
            }
        }
    }
}

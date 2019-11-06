package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.About
import com.miriga.rieng.databinding.ListItemAboutBinding
import com.miriga.rieng.ui.about.AboutItemViewModel


class AboutAdapter :
    ListAdapter<About, AboutAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind( item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAboutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    

    class ViewHolder(
        private val binding: ListItemAboutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind( item: About) {
            binding.apply {
                viewModel = AboutItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<About>() {
            override fun areItemsTheSame(
                oldItem: About,
                newItem: About
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: About,
                newItem: About
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.HelpCenter
import com.miriga.rieng.databinding.ListHelpcenterItemBinding
import com.miriga.rieng.ui.help.HelpCenterItemViewModel

class HelpCenterAdapter :
    ListAdapter<HelpCenter, HelpCenterAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListHelpcenterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class ViewHolder(
        private val binding: ListHelpcenterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HelpCenter) {
            binding.apply {
                viewModel = HelpCenterItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<HelpCenter>() {
            override fun areItemsTheSame(
                oldItem: HelpCenter,
                newItem: HelpCenter
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HelpCenter,
                newItem: HelpCenter
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

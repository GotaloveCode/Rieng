package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.Category
import com.miriga.rieng.databinding.ListItemTopicBinding
import com.miriga.rieng.ui.topics.TopicItemViewModel
import com.miriga.rieng.ui.topics.TopicsFragmentDirections


class TopicAdapter :
    ListAdapter<Category, TopicAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemTopicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(item: Category): View.OnClickListener {

        return View.OnClickListener {
            val action = TopicsFragmentDirections.actionTopicsFragmentToSubTopicFragment(item.category,item.id)
            it.findNavController().navigate(action)
        }
    }

    class ViewHolder(
        private val binding: ListItemTopicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Category) {
            binding.apply {
                clickListener = listener
                viewModel = TopicItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
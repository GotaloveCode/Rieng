package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.ChildCategory
import com.miriga.rieng.databinding.ListSubtopicItemBinding
import com.miriga.rieng.ui.subtopics.SubTopicFragmentDirections
import com.miriga.rieng.ui.subtopics.SubTopicItemViewModel


class SubTopicAdapter :
    ListAdapter<ChildCategory, SubTopicAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListSubtopicItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(item: ChildCategory): View.OnClickListener {
        return View.OnClickListener {
            val action = SubTopicFragmentDirections.actionSubTopicFragmentToArticlesFragment(item.category, item.id)
            it.findNavController().navigate(action)
        }
    }

    class ViewHolder(
        private val binding: ListSubtopicItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: ChildCategory) {
            binding.apply {
                clickListener = listener
                viewModel = SubTopicItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<ChildCategory>() {
            override fun areItemsTheSame(
                oldItem: ChildCategory,
                newItem: ChildCategory
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ChildCategory,
                newItem: ChildCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
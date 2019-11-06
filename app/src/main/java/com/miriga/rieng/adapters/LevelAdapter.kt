package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.Level
import com.miriga.rieng.databinding.ListItemLevelBinding
import com.miriga.rieng.ui.levels.LevelItemViewModel
import com.miriga.rieng.ui.levels.LevelFragmentDirections


class LevelAdapter :
    ListAdapter<Level, LevelAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemLevelBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(item: Level): View.OnClickListener {
        return View.OnClickListener {
            val action = LevelFragmentDirections.actionLevelFragmentToQuizFragment(item.id,item.level)
            it.findNavController().navigate(action)
        }
    }

    class ViewHolder(
        private val binding: ListItemLevelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Level) {
            binding.apply {
                clickListener = listener
                viewModel = LevelItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Level>() {
            override fun areItemsTheSame(
                oldItem: Level,
                newItem: Level
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Level,
                newItem: Level
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
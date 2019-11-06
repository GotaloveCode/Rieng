package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.Choice
import com.miriga.rieng.databinding.ListItemAnswerBinding
import com.miriga.rieng.ui.quiz.AnswerItemViewModel


class AnswerAdapter(val clickListener: (Choice) -> Unit) :
    ListAdapter<Choice, AnswerAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(choice: Choice): View.OnClickListener {
        return View.OnClickListener {
            clickListener(choice)
        }
    }


    class ViewHolder(private val binding: ListItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Choice) {
            binding.apply {
                clickListener = listener
                viewModel = AnswerItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Choice>() {
            override fun areItemsTheSame(
                oldItem: Choice,
                newItem: Choice
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Choice,
                newItem: Choice
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
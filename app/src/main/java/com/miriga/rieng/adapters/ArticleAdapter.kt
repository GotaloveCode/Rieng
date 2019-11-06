package com.miriga.rieng.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miriga.rieng.data.gson.Article
import com.miriga.rieng.databinding.ListItemArticleBinding
import com.miriga.rieng.ui.article.ArticleItemViewModel
import com.miriga.rieng.ui.article.ArticlesFragmentDirections


class ArticleAdapter :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(ITEM_COMPARATOR) {

    private lateinit var _title:String

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(item: Article): View.OnClickListener {
        return View.OnClickListener {
            val action = ArticlesFragmentDirections.actionArticlesFragmentToArticleDetailFragment(
                item,
                _title
            )
            it.findNavController().navigate(action)
        }
    }

    fun setTitle(title: String) {
        _title = title
    }


    class ViewHolder(
        private val binding: ListItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Article) {
            binding.apply {
                clickListener = listener
                viewModel = ArticleItemViewModel(item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
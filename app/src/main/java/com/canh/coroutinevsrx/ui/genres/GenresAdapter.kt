package com.canh.coroutinevsrx.ui.genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.canh.coroutinevsrx.R
import com.canh.coroutinevsrx.data.model.Genre
import com.canh.coroutinevsrx.databinding.AdapterItemGenreBinding
import com.canh.coroutinevsrx.ui.base.BaseRecyclerAdapter
import com.canh.coroutinevsrx.util.setClickSafe

class GenresAdapter(private val callback: ((Genre) -> Unit)?) :
    BaseRecyclerAdapter<Genre>(callBack = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
    ) {
    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding =
        DataBindingUtil.inflate<AdapterItemGenreBinding>(
            LayoutInflater.from(parent.context), R.layout.adapter_item_genre,
            parent, false
        ).apply {
            root.setClickSafe(View.OnClickListener {
                this.genre?.let { item ->
                    callback?.invoke(item)
                }
            })
        }


    override fun bind(binding: ViewDataBinding, item: Genre) {
        if (binding is AdapterItemGenreBinding) binding.genre = item
    }
}

package com.canh.coroutinevsrx.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.canh.coroutinevsrx.R
import com.canh.coroutinevsrx.data.model.Movie
import com.canh.coroutinevsrx.databinding.AdapterItemMovieBinding
import com.canh.coroutinevsrx.ui.base.BaseRecyclerAdapter
import com.canh.coroutinevsrx.util.setClickSafe

class MoviesAdapter(private val callback: ((Movie) -> Unit)?) : BaseRecyclerAdapter<Movie>(
    callBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding =
        DataBindingUtil.inflate<AdapterItemMovieBinding>(
            LayoutInflater.from(parent.context), R.layout.adapter_item_movie,
            parent, false
        ).apply {
            root.setClickSafe(View.OnClickListener {
                this.movie?.let { item ->
                    callback?.invoke(item)
                }
            })
        }

    override fun bind(binding: ViewDataBinding, item: Movie) {
        if (binding is AdapterItemMovieBinding) binding.movie = item
    }

}

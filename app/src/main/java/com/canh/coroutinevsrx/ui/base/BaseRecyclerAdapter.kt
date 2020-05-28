package com.canh.coroutinevsrx.ui.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T>(
    callBack: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<ViewDataBinding>>(
    AsyncDifferConfig.Builder<T>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> {
        return BaseViewHolder(createBinding(parent = parent, viewType = viewType))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        bind(holder.binding, getItem(position))
        bindPosition(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    open fun bindPosition(binding: ViewDataBinding, item: T, position: Int) {
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int? = 0): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, item: T)
}

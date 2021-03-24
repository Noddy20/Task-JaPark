package com.demo.japark.uiModules.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<B : ViewDataBinding>(protected val mDataBinding: B) :
    RecyclerView.ViewHolder(mDataBinding.root) {

    open fun bind(pos: Int) {
        mDataBinding.executePendingBindings()
    }
}
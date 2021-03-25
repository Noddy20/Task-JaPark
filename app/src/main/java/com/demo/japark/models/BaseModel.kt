package com.demo.japark.models

import androidx.recyclerview.widget.DiffUtil

interface BaseModel

fun <T> getDiffUtil(areContentsTheSame: (oldItem: T, newItem: T) -> Boolean) = object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame(oldItem, newItem)

}
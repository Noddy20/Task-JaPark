package com.demo.japark.uiModules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import com.demo.japark.databinding.ItemHomeCityLayoutBinding
import com.demo.japark.models.data.ModelJapanCity
import com.demo.japark.uiModules.base.BaseViewHolder
import com.demo.japark.utils.extFunctions.coroutineViewBinding.launchViewClick
import com.demo.japark.utils.extFunctions.invoke
import javax.inject.Inject

class AdapterHomeCities @Inject constructor(private val mLifecycleScope: LifecycleCoroutineScope) :
        ListAdapter<ModelJapanCity, AdapterHomeCities.MyViewHolder>(ModelJapanCity.DIFF_UTIL) {

    var onClickItem: ((ModelJapanCity, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(ItemHomeCityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MyViewHolder(mDataBinding: ItemHomeCityLayoutBinding):
            BaseViewHolder<ItemHomeCityLayoutBinding>(mDataBinding){

        init {
            mDataBinding{
                itemRootView.launchViewClick(mLifecycleScope) {
                    onClickItem?.invoke(getItem(adapterPosition), ivCity)
                }
            }
        }

        override fun bind(pos: Int) {
            mDataBinding{
                bModel = getItem(adapterPosition)
            }
            super.bind(pos)
        }

    }

}
package com.demo.japark.uiModules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import com.demo.japark.databinding.ItemHomeFoodLayoutBinding
import com.demo.japark.models.data.ModelJapanFood
import com.demo.japark.uiModules.base.BaseViewHolder
import com.demo.japark.utils.extFunctions.coroutineViewBinding.launchViewClick
import com.demo.japark.utils.extFunctions.invoke
import javax.inject.Inject

class AdapterHomeFoods @Inject constructor(private val mLifecycleScope: LifecycleCoroutineScope) :
    ListAdapter<ModelJapanFood, AdapterHomeFoods.MyViewHolder>(ModelJapanFood.DIFF_UTIL) {

    var onClickItem: ((ModelJapanFood, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemHomeFoodLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MyViewHolder(mDataBinding: ItemHomeFoodLayoutBinding):
        BaseViewHolder<ItemHomeFoodLayoutBinding>(mDataBinding){

        init {
            mDataBinding{
                itemRootView.launchViewClick(mLifecycleScope){
                    onClickItem?.invoke(getItem(adapterPosition), ivFood)
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
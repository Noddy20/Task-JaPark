package com.demo.japark.uiModules.home

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import com.demo.japark.databinding.ItemHomeCityLayoutBinding
import com.demo.japark.uiModules.base.BaseViewHolder
import com.demo.japark.utils.extFunctions.coroutineViewBinding.launchViewClick
import com.demo.japark.utils.extFunctions.invoke

class AdapterHomeCities(private val mLifecycleScope: LifecycleCoroutineScope) : ListAdapter<> {


    inner class MyViewHolder private constructor(mDataBinding: ItemHomeCityLayoutBinding): BaseViewHolder<ItemHomeCityLayoutBinding>(mDataBinding){

        init {
            mDataBinding{
                itemRootView.launchViewClick(mLifecycleScope) {

                }
            }
        }

        override fun bind(pos: Int) {
            mDataBinding{

            }
            super.bind(pos)
        }

    }

}
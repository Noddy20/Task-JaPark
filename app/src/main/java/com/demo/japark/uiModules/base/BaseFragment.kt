package com.demo.japark.uiModules.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.demo.japark.utils.extFunctions.inflateBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 *    This file is not used in the project because there is no fragment
 */

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    @Inject
    @ApplicationContext
    lateinit var mAppContext: Context

    @Inject
    lateinit var mActivity: Activity

    protected lateinit var mDataBinding: B
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = inflater.inflateBinding(layoutId, container)
        mDataBinding.lifecycleOwner = viewLifecycleOwner
        return mDataBinding.root
    }

}
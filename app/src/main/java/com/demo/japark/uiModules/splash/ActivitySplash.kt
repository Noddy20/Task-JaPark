package com.demo.japark.uiModules.splash

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.demo.japark.R
import com.demo.japark.constants.AppConstants
import com.demo.japark.databinding.ActivitySplashBinding
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.uiModules.gotoHomeActivity
import com.demo.japark.utils.extFunctions.*
import com.demo.japark.utils.extFunctions.coroutineViewBinding.onTransitionEnd
import com.transitionseverywhere.Recolor
import com.transitionseverywhere.extra.Scale
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ActivitySplash : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivitySplashBinding>(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{
            setFullScreen()
            clAppNames.post {                                                                       //Wait until the layout is ready to animate
                splashTransition()
            }
        }
    }

    override fun onStart() {
        if (isActivityNotFullScreen()) {
            setFullScreen()
        }
        super.onStart()
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

    private fun ActivitySplashBinding.splashTransition(){
        lifecycleScope.launchWhenResumed {                                                          // This code will only execute if lifecycle state is onResumed
            TransitionSet().apply {
                duration = AppConstants.SPLASH_SCREEN_TRANSITION_DELAY
                addTransition(Fade().addTarget(clAppNames))
                addTransition(Scale().addTarget(clAppNames))
                addTransition(Recolor().addTarget(tvAppName))
            }.let { transitionSet ->
                transitionSet.onTransitionEnd(lifecycleScope){
                    Timber.d("onTransitionEnd")
                    gotoHomeActivity()
                    finish()
                }
                clAppNames.post {
                    TransitionManager.beginDelayedTransition(actRootView, transitionSet)
                    Timber.d("onTransition beginDelayedTransition ${lifecycle.currentState}")
                    clAppNames.isVisible = true
                    tvAppName.setTextColor(getColorCompat(R.color.colorAccent))
                }
            }
        }
    }

}
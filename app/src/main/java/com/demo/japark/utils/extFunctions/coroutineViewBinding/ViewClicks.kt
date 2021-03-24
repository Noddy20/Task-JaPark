package com.demo.japark.utils.extFunctions.coroutineViewBinding

import android.view.View
import androidx.annotation.CheckResult
import com.demo.japark.constants.AppConstants
import com.demo.japark.utils.extFunctions.launchWithExcHandler
import com.demo.japark.utils.extFunctions.throttleFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.isActive

/**
 *    View Clicks with coroutines and throttle support like RxViewBinding
 */


fun <T : View?> Array<T?>.launchViewClicks(coroutineScope: CoroutineScope, throttle: Long = AppConstants.VIEW_CLICK_THROTTLE_FIRST, onClick: (view: View) -> Unit) {
    val flows = ArrayList<Flow<View>>()
    this.forEach { view ->
        view?.let { flows.add(view.clicks().throttleFirst(throttle)) }
    }
    coroutineScope.launchWithExcHandler {
        flows.merge().collect {
            onClick(it)
        }
    }
}

fun <T : View?> T?.launchViewClick(coroutineScope: CoroutineScope, throttle: Long = AppConstants.VIEW_CLICK_THROTTLE_FIRST, onClick: (view: View) -> Unit) {
    coroutineScope.launchWithExcHandler {
        this@launchViewClick?.let {view->
            view.clicks().throttleFirst(throttle).collect {
                onClick(it)
            }
        }
    }
}

@CheckResult
fun View.clicks(): Flow<View> = channelFlow {
    setOnClickListener(listener(this, ::offer))
    awaitClose { setOnClickListener(null) }
}

@CheckResult
private fun View.listener(
    scope: CoroutineScope,
    emitter: (View) -> Boolean
) = View.OnClickListener {
    if (scope.isActive) { emitter(this) }
}

inline fun <reified T : View?> views(vararg elements: T?): Array<T?> {
    return arrayOf(*elements)
}

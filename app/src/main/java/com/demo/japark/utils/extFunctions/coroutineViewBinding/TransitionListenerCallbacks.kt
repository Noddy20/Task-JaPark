package com.demo.japark.utils.extFunctions.coroutineViewBinding

import androidx.annotation.CheckResult
import androidx.transition.Transition
import com.demo.japark.utils.extFunctions.launchWithExcHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive

/**
 *    Transition Callbacks with coroutines support like RxViewBinding
 */

fun Transition.onTransitionEnd(
    coroutineScope: CoroutineScope,
    onTransitionEnd: (Transition) -> Unit
) {
    coroutineScope.launchWithExcHandler {
        channelFlow {
            val listener = onTransitionEndListener(this, emitterOnEnd = ::offer)
            addListener(listener)
            awaitClose { removeListener(listener) }
        }.collect {
            onTransitionEnd(it)
        }
    }
}

/**
 *   Only onTransitionEnd is implemented above but we can also implement other callbacks same as onTransitionEnd
 */

@CheckResult
private fun onTransitionEndListener(
    scope: CoroutineScope,
    emitterOnStart: ((Transition) -> Boolean)? = null,
    emitterOnEnd: ((Transition) -> Boolean)? = null,
    emitterOnCancel: ((Transition) -> Boolean)? = null,
    emitterOnPause: ((Transition) -> Boolean)? = null,
    emitterOnResume: ((Transition) -> Boolean)? = null
) = object : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition) {
        if (scope.isActive) { emitterOnStart?.invoke(transition) }
    }

    override fun onTransitionEnd(transition: Transition) {
        if (scope.isActive) { emitterOnEnd?.invoke(transition) }
    }

    override fun onTransitionCancel(transition: Transition) {
        if (scope.isActive) { emitterOnCancel?.invoke(transition) }
    }

    override fun onTransitionPause(transition: Transition) {
        if (scope.isActive) { emitterOnPause?.invoke(transition) }
    }

    override fun onTransitionResume(transition: Transition) {
        if (scope.isActive) { emitterOnResume?.invoke(transition) }
    }
}
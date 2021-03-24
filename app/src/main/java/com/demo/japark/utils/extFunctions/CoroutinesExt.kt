package com.demo.japark.utils.extFunctions

import com.demo.japark.utils.DefaultExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchWithExcHandler(
    context: CoroutineContext = coroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context + DefaultExceptionHandler.coroutineExceptionHandler) {
        block()
    }
}

// throttleFirst like RxJava Operator

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}
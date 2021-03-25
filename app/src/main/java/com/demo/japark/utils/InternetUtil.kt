package com.demo.japark.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.utils.extFunctions.getNetState
import com.demo.japark.utils.extFunctions.isNetConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 *   InternetUtil to get realtime updates of network connectivity changes
 *   by using LiveData so an observer can observe the status with it's lifecycle
 */

class InternetUtil @Inject constructor(@ApplicationContext private val appContext: Context) : LiveData<SealedNetState>() {

    companion object {
        var CURRENT_NET_STATE: SealedNetState = SealedNetState.NotAvailable.UnAvailable
    }

    private val connMan = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private var lastStateAvailability = true

    val isNetConnected: Boolean
        get() {
            return appContext.isNetConnected(connMan)
        }

    val netState: SealedNetState
        get() {
            return appContext.getNetState(connMan)
        }

    init {
        CURRENT_NET_STATE = netState
    }

    private val networkStateObject = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            postState(SealedNetState.NotAvailable.Lost)
            super.onLost(network)
        }

        override fun onUnavailable() {
            postState(SealedNetState.NotAvailable.UnAvailable)
            super.onUnavailable()
        }

        override fun onAvailable(network: Network) {
            postState(SealedNetState.Available)
            super.onAvailable(network)
        }

        private fun postState(state: SealedNetState) {
            Timber.d("postState $state ${state != CURRENT_NET_STATE} ${hasActiveObservers()}")
            if (hasActiveObservers() && state != CURRENT_NET_STATE) {
                CURRENT_NET_STATE = state
                postValue(CURRENT_NET_STATE)
                lastStateAvailability = true
            }
        }
    }

    fun registerObserver() {
        if (!hasActiveObservers()) {
            connMan?.registerNetworkCallback(networkRequestBuilder(), networkStateObject)
        }
    }

    private fun networkRequestBuilder(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

}

//To prevent livedata observer from receiving data again and again
inline fun LiveData<SealedNetState>.observeNetState(owner: LifecycleOwner, crossinline onEventUnhandledContent: (SealedNetState) -> Unit) {
    observe(owner, { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}


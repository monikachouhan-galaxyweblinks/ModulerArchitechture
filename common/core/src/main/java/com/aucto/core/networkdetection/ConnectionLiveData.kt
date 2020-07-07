package com.aucto.core.networkdetection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

/**
 * Detect network with type (WIFI,CELLULAR etc.)
 * @see ConnectionLiveData
 */
class ConnectionLiveData(private val context: Context) : LiveData<ConnectionModel>() {

    private var networkJob: Job? = null

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            broadcastNetworkState(intent)
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }

    private fun broadcastNetworkState(intent: Intent) {
        networkJob?.cancel()
        networkJob = GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            if (intent.extras != null) {
                val activeNetwork =
                    intent.extras!!.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo?
                val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    when (activeNetwork!!.type) {
                        ConnectivityManager.TYPE_WIFI -> postValue(
                            ConnectionModel(ConnectionType.WIFI, true)
                        )
                        ConnectivityManager.TYPE_MOBILE -> postValue(
                            ConnectionModel(ConnectionType.CELLULAR, true)
                        )
                    }
                } else {
                    postValue(ConnectionModel(ConnectionType.NONE, false))
                }
            }
        }
    }
}

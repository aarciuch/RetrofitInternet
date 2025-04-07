package psm.lab.retrofitinternet.UDP

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.bundle.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import psm.lab.retrofitinternet.retrofit.ApiService

class UdpVM(application: Application) : AndroidViewModel(application) {
    private val connectivityManager =
        application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork = connectivityManager.activeNetwork
    private val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    private val currentApiVersion = Build.VERSION.SDK_INT
    private var _capabilities = MutableStateFlow(Bundle())
    val capabilities = _capabilities.asStateFlow()


    fun getNetworkCapabilities() {
        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
            val wifiManager =
                getApplication<Application>().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val linkDownstreamBandwidthKbps = networkCapabilities.linkDownstreamBandwidthKbps
            val linkUpstreamBandwidthKbps = networkCapabilities.linkUpstreamBandwidthKbps
            val signalStrength = networkCapabilities.signalStrength
        }
    }

    fun isPremissionGranted(perm : String) : Boolean{
        val context = getApplication<Application>().applicationContext
        return ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }

    init {

    }

}
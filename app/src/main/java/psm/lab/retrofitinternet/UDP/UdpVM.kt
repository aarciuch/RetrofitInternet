package psm.lab.retrofitinternet.UDP

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.bundle.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import psm.lab.retrofitinternet.retrofit.ApiService
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket

class UdpVM(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val _acceessFineLocationPermissionGranted = MutableStateFlow(false)
    val acceessFineLocationPermissionGranted: StateFlow<Boolean> = _acceessFineLocationPermissionGranted



    fun checkPermission_ACCESS_FINE_LOCATION()  {
        _acceessFineLocationPermissionGranted.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }



    private val _networkSettings = MutableStateFlow<NetworkSettings?>(null)
    val networkSettings: StateFlow<NetworkSettings?> = _networkSettings

    init {
        fetchNetworkSettings()
    }

    private fun fetchNetworkSettings() {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return

        val ssid = if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            val transportInfo = networkCapabilities.transportInfo
            if (transportInfo is WifiInfo) transportInfo.ssid else "Unknown"
        } else "Not Wi-Fi"

        val linkDown = networkCapabilities.linkDownstreamBandwidthKbps.toString() // Przykład
        val signalStrength = networkCapabilities.signalStrength ?: -1

        val ipAddress = ""


        _networkSettings.value = NetworkSettings(
            ssid = ssid,
            ipAddress = ipAddress,
            signalStrength = signalStrength,
            linkDown = linkDown
        )

        Log.i("RETROFIT", "${_networkSettings.value}")
    }

    private fun convertIntToIpAddress(ip: Int): String {
        return "${ip and 0xFF}.${ip shr 8 and 0xFF}.${ip shr 16 and 0xFF}.${ip shr 24 and 0xFF}"
    }
}

data class NetworkSettings(
    val ssid: String,
    val ipAddress: String,
    val signalStrength: Int,
    val linkDown : String
)


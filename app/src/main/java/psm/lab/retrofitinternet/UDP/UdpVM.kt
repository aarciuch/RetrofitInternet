package psm.lab.retrofitinternet.UDP

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UdpVM(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val _acceessFineLocationPermissionGranted = MutableStateFlow(false)
    val acceessFineLocationPermissionGranted: StateFlow<Boolean> = _acceessFineLocationPermissionGranted
    private var _receivedMessage = MutableStateFlow("")
    val receivedMessage : StateFlow<String> = _receivedMessage



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

    }

    private fun convertIntToIpAddress(ip: Int): String {
        return "${ip and 0xFF}.${ip shr 8 and 0xFF}.${ip shr 16 and 0xFF}.${ip shr 24 and 0xFF}"
    }

    fun sendUdp(message: String, ip: String, port: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val socket = DatagramSocket()
                val data = message.toByteArray()
                val packet = DatagramPacket(data, data.size, InetAddress.getByName(ip), port)
                socket.send(packet)
                socket.close()
            }
        }
    }

    fun listenUdp(startListen: Boolean, port: Int) {
        var socket: DatagramSocket? = null
        val buffer = ByteArray(1024)
        var packet = DatagramPacket(buffer, buffer.size)
        if (startListen) {
            try {
                socket = DatagramSocket(port)
                packet = DatagramPacket(buffer, buffer.size)
                socket!!.receive(packet)
                _receivedMessage.value = String(packet.data, Charsets.UTF_8)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket?.close()
            }
        } else {
            socket?.close()
        }
    }
}

data class NetworkSettings(
    val ssid: String,
    val ipAddress: String,
    val signalStrength: Int,
    val linkDown : String
)


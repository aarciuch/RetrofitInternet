package psm.lab.retrofitinternet.pages

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.androidx.compose.koinViewModel
import psm.lab.retrofitinternet.UDP.UdpVM

@Composable
fun Page2(udpVM: UdpVM = koinViewModel()) {
    val isGranted_ACCESS_FINE_LOACTION_PERMISSION by udpVM.acceessFineLocationPermissionGranted.collectAsState()

    LaunchedEffect(Unit) {
        udpVM.checkPermission_ACCESS_FINE_LOCATION()
    }

    Column()
    {
        if (!isGranted_ACCESS_FINE_LOACTION_PERMISSION) {
            PermissionScreen()
        } else {
           UDPComm()
        }
    }


}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen() {
    val permission = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val permissionState = rememberMultiplePermissionsState(permissions = permission)
    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
            Text(text = "Requset permission")
        }
        permissionState.permissions.forEach { permission ->
            when (permission.permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    Text(
                        text = if (permission.status.isGranted) {
                            "ACCESS_FINE_LOCATION permission granted"
                        } else {
                            "ACCESS_FINE_LOCATION permission denied"
                        }
                    )
                }
            }
        }
    }*/
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { openDialog.value = false },
            content = {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Permission ACCESS_FINE_LOCATION is required",
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        )
                        {

                            TextButton(
                                onClick = {
                                    permissionState.launchMultiplePermissionRequest()
                                    openDialog.value = false
                                },
                            ) {
                                Text("Grant", fontSize = 30.sp)
                            }
                            TextButton(
                                onClick = { openDialog.value = false },
                            ) {
                                Text("Deny", fontSize = 30.sp)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun UDPComm(udpVM: UdpVM = koinViewModel()) {

}






package psm.lab.retrofitinternet.pages

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    var permissionIsGranted by remember { mutableStateOf(false) }
    Column(
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
                    if (permission.status.isGranted) {
                        Text(text = "ACCESS_FINE_LOCATION permission granted")
                        permissionIsGranted = true
                    } else {
                        Text(text = "ACCESS_FINE_LOCATION permission denied")
                    }
                }
            }
        }
        if (permissionIsGranted) UDPComm()
    }
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

//nc -ukl port - od słuchania na UDP na linuxie
//nc -u adresIP port do wysyalania na UDP na linuxie
@Composable
fun UDPComm(udpVM: UdpVM = koinViewModel()) {
    var ip by remember { mutableStateOf("10.7.38.161") }
    var port by remember { mutableStateOf(5000) }
    var message by remember { mutableStateOf("message") }
    var recivedMessage = udpVM.receivedMessage.collectAsState().value
    var listen by remember { mutableStateOf(false) }

    Text("UDPComm")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Blue)
    ) {
        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("IP ADDRESS") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = port.toString(),
            onValueChange = { port = it.toInt() },
            label = { Text("NR PORTU UDP") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { udpVM.sendUdp("${message}\n", ip, port) }, modifier = Modifier.fillMaxWidth()) {
            Text("Send")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Blue)
    ) {

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            listen = true
            GlobalScope.launch {
                while (listen) {
                    udpVM.listenUdp(listen, 5000)
                }
           }
        }
        ) {

            Text("Start listen ")
        }
        Button(modifier = Modifier.fillMaxWidth(),onClick = {
            listen = false
            udpVM.listenUdp(listen, 5000)
        }) {
            Text("Stop listen ")
        }
        Text(modifier = Modifier.fillMaxWidth(), text = "Received: ${recivedMessage}")
    }
}





